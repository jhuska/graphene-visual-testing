package org.arquillian.graphene.visual.testing.impl;

import org.arquillian.graphene.visual.testing.impl.jaxbmodel.VisualSuiteResult;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.arquillian.extension.recorder.screenshooter.ScreenshooterConfiguration;
import org.arquillian.graphene.visual.testing.api.SamplesAndDiffsHandler;
import org.arquillian.graphene.visual.testing.configuration.GrapheneVisualTestingConfiguration;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.rusheye.arquillian.configuration.RusheyeConfiguration;
import org.jboss.rusheye.arquillian.event.ParsingDoneEvent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.SAXException;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import org.jboss.rusheye.suite.ResultConclusion;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jhuska
 */
public class JCRSamplesAndDiffsHandler implements SamplesAndDiffsHandler {

    private static final Logger LOGGER = Logger.getLogger(JCRSamplesAndDiffsHandler.class.getName());

    @Inject
    private Instance<RusheyeConfiguration> rusheyeConf;

    @Inject
    private Instance<GrapheneVisualTestingConfiguration> grapheneVisualTestingConf;

    @Inject
    private Instance<ScreenshooterConfiguration> screenshooterConf;

    private Document resultXML = null;

    @Override
    public void saveSamplesAndDiffs(@Observes ParsingDoneEvent parsingDoneEvent) {
        Date timestamp = new Date();
        String timestampWithoutWhiteSpaces = timestamp.toString().replaceAll("\\s+", "_").replaceAll(":", "_");
        String suiteName = grapheneVisualTestingConf.get().getTestSuiteName();
        CloseableHttpClient httpclient = RestUtils.getHTTPClient();
        File resultDescriptor = new File(rusheyeConf.get().getWorkingDirectory()
                + File.separator
                + rusheyeConf.get().getResultOutputFile());

        //UPLOADING RESULT DESCRIPTOR
        HttpPost postResultDescriptor = new HttpPost(RestUtils.JCR_REPOSITORY_URL + "/upload/" + suiteName + "/runs/"
                + timestampWithoutWhiteSpaces + "/result.xml");
        FileEntity descriptorEntity = new FileEntity(resultDescriptor, ContentType.APPLICATION_XML);
        postResultDescriptor.setEntity(descriptorEntity);
        RestUtils.executePost(postResultDescriptor, httpclient,
                String.format("Suite result descriptor for %s uploaded!", suiteName),
                String.format("Error while uploading test suite result descriptor for test suite: %s", suiteName));

        //UPLOADING DIFFS AND SAMPLES IF ANY
        List<String> diffNames = getDiffNames();
        if (!diffNames.isEmpty()) {
            uploadSamples(timestampWithoutWhiteSpaces);
            File diffsDir = new File(rusheyeConf.get().getWorkingDirectory()
                    + File.separator
                    + rusheyeConf.get().getDiffsDir());
            uploadDiffs(diffNames, diffsDir, timestampWithoutWhiteSpaces);
        }
    }

    private void uploadSamples(String timestamp) {
        final File screenshotsDir = screenshooterConf.get().getRootDir();
        final String suiteName = grapheneVisualTestingConf.get().getTestSuiteName();
        final String urlToSaveAt = "/runs/" + timestamp + "/samples/";
        RestUtils.crawlAndUploadScreenshots(screenshotsDir, screenshotsDir.getName(), suiteName, urlToSaveAt);
    }

    private void uploadDiffs(List<String> diffNames, File diffsDir, String timestampWithoutWhiteSpaces) {
        String suiteName = grapheneVisualTestingConf.get().getTestSuiteName();
        for (final String diffName : diffNames) {
            File[] diffsWithSearchedName = diffsDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains(diffName);
                }
            });
            if (diffsWithSearchedName.length > 1) {
                throw new RuntimeException("There are two or more diffs which names contains: " + diffName);
            } else if (diffsWithSearchedName.length == 0) {
                throw new RuntimeException("Diff with filename: " + diffName + " was not found!");
            } else {
                //UPLOADING DIFF
                File diffToUpload = diffsWithSearchedName[0];
                HttpPost postResultDescriptor = new HttpPost(RestUtils.JCR_REPOSITORY_URL + "/upload/" + suiteName + "/runs/"
                        + timestampWithoutWhiteSpaces + "/diffs/" + diffName);
                FileEntity diffEntity = new FileEntity(diffToUpload);
                postResultDescriptor.setEntity(diffEntity);
                RestUtils.executePost(postResultDescriptor, RestUtils.getHTTPClient(),
                        String.format("Diff for %s uploaded!", suiteName),
                        String.format("Error while uploading diff for test suite: %s", suiteName));

            }
        }
    }

    private List<String> getDiffNames() {
        List<String> result = new ArrayList<>();
        NodeList testNodes = getDOMLazilyFromResultXML().getElementsByTagName("test");
        for (int i = 0; i < testNodes.getLength(); i++) {
            Node patternTag = testNodes.item(i).getChildNodes().item(1);
            String resultAttribute = patternTag.getAttributes().
                    getNamedItem("result").getNodeValue();
            if (ResultConclusion.valueOf(resultAttribute).equals(ResultConclusion.DIFFER)) {
                result.add(patternTag.getAttributes().getNamedItem("output").getNodeValue());
            }
        }
        return result;
    }

    private Document getDOMLazilyFromResultXML() {
        if (this.resultXML == null) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db;
                db = dbf.newDocumentBuilder();
                String resultFilePath = rusheyeConf.get().getWorkingDirectory()
                        + File.separator + rusheyeConf.get().getResultOutputFile();
                this.resultXML = db.parse(new File(resultFilePath));
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(JCRSamplesAndDiffsHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(JCRSamplesAndDiffsHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JCRSamplesAndDiffsHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.resultXML;
    }

    private void postResultXML(Date timestamp) {
        HttpPost postSuiteDescriptor = new HttpPost(RestUtils.JCR_REPOSITORY_URL + "/upload/"
                + grapheneVisualTestingConf.get().getTestSuiteName() + "/runs/");
    }

    public Instance<RusheyeConfiguration> getRusheyeConf() {
        return rusheyeConf;
    }

    public void setRusheyeConf(Instance<RusheyeConfiguration> rusheyeConf) {
        this.rusheyeConf = rusheyeConf;
    }

    public Instance<GrapheneVisualTestingConfiguration> getGrapheneVisualTestingConf() {
        return grapheneVisualTestingConf;
    }

    public void setGrapheneVisualTestingConf(Instance<GrapheneVisualTestingConfiguration> grapheneVisualTestingConf) {
        this.grapheneVisualTestingConf = grapheneVisualTestingConf;
    }
}
