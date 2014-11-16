package org.arquillian.graphene.visual.testing.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.arquillian.extension.recorder.screenshooter.ScreenshooterConfiguration;
import org.arquillian.graphene.visual.testing.api.DescriptorAndPatternsHandler;
import org.arquillian.graphene.visual.testing.configuration.GrapheneVisualTestingConfiguration;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.rusheye.arquillian.configuration.RusheyeConfiguration;

/**
 *
 * @author jhuska
 */
public class JCRDescriptorAndPatternsHandler implements DescriptorAndPatternsHandler {

    private static final String JCR_PROTOCOL = "http";
    private static final String JCR_HOST = "localhost";
    private static final Integer JCR_PORT = 8080;

    private static final String JCR_USER_NAME = "graphene-visual-testing";
    private static final String JCR_PASSWORD = "graphene-visual-testing";

    private static final String JCR_REPOSITORY_URL = "http://localhost:8080/modeshape-rest/graphene-visual-testing/default";

    private static final Logger LOGGER = Logger.getLogger(JCRDescriptorAndPatternsHandler.class.getName());

    @Inject
    private Instance<ScreenshooterConfiguration> screenshooterConf;

    @Inject
    private Instance<RusheyeConfiguration> rusheyeConf;

    @Inject
    private Instance<GrapheneVisualTestingConfiguration> grapheneVisualTestingConf;

    @Override
    public boolean saveDescriptorAndPatterns() {
        CloseableHttpClient httpclient = getHTTPClient();

        File patternsRootDir = screenshooterConf.get().getRootDir();
        File suiteDescriptor = new File(rusheyeConf.get().getWorkingDirectory().getAbsolutePath()
                + File.separator
                + rusheyeConf.get().getSuiteDescriptor());
        String suiteName = grapheneVisualTestingConf.get().getTestSuiteName();

        //UPLOADING TEST SUITE DESCRIPTOR
        HttpPost postSuiteDescriptor = new HttpPost(JCR_REPOSITORY_URL + "/upload/" + suiteName + "/suite.xml");
        FileEntity descriptorEntity = new FileEntity(suiteDescriptor, ContentType.APPLICATION_XML);
        postSuiteDescriptor.setEntity(descriptorEntity);
        executePost(postSuiteDescriptor, httpclient,
                String.format("Suite descriptor for %s uploaded!", suiteName),
                String.format("Error while uploading test suite descriptor for test suite: %s", suiteName));

        //UPLOADING PATTERNS
        return crawlPatternsAndUploadThem(patternsRootDir, patternsRootDir.getName(), httpclient);
    }
    
    @Override
    public String retrieveDescriptorAndPatterns() {
        CloseableHttpClient httpclient = getHTTPClient();
        File patternsDir = new File("target/patterns");
        String suiteName = grapheneVisualTestingConf.get().getTestSuiteName();
        
        HttpGet getDescriptor = new HttpGet(JCR_REPOSITORY_URL + "/items/" + suiteName + "/suite.xml");
        executeGet(getDescriptor, httpclient, 
                String.format("Suite descriptor for %s was retrieved.", suiteName), 
                String.format("ERROR occurred while retrieving suite descriptor for %s", suiteName));
        return null;
    }

    private boolean crawlPatternsAndUploadThem(File patternsDir, String rootOfPatterns, CloseableHttpClient httpClient) {
        boolean result = true;
        for (File dirOrFile : patternsDir.listFiles()) {
            if (dirOrFile.isDirectory()) {
                result = crawlPatternsAndUploadThem(dirOrFile, rootOfPatterns, httpClient);
            } else {
                String suiteName = grapheneVisualTestingConf.get().getTestSuiteName();
                String absolutePath = dirOrFile.getAbsolutePath();
                String patternRelativePath = absolutePath.split(rootOfPatterns + File.separator)[1];
                HttpPost postPattern = new HttpPost(JCR_REPOSITORY_URL + "/upload/"
                        + suiteName + "/patterns/"
                        + patternRelativePath);
                FileEntity screenshot = new FileEntity(dirOrFile);
                postPattern.setEntity(screenshot);
                result = executePost(postPattern, httpClient,
                        String.format("Pattern: %s uploaded to test suite: %s", dirOrFile.getName(), suiteName),
                        String.format("ERROR: pattern %s was not uploaded to test suite %s", dirOrFile.getName(), suiteName));
            }
            //if partial result is false, finish early with false status
            if(!result) {
                return result;
            }
        }
        return result;
    }

    private String executeGet(HttpGet httpGet, CloseableHttpClient httpclient, String successLog, String errorLog) {
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != 200) {
                if (errorLog != null) {
                    LOGGER.severe(errorLog);
                }
                return null;
            }
            HttpEntity entity = response.getEntity();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuffer result = new StringBuffer();
            String line = bfr.readLine();
            while (line != null) {
                result.append(line);
                line = bfr.readLine();
            }
            EntityUtils.consume(entity);
            if (successLog != null) {
                LOGGER.info(successLog);
            }
            return result.toString();
        } catch (IOException ex) {
            if (errorLog != null) {
                LOGGER.severe(String.format("%s %s", errorLog, ex.getMessage()));
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
            }
        }
        return null;
    }

    private boolean executePost(HttpPost post, CloseableHttpClient httpclient, String successLog, String errorLog) {
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(post);
            if (!isOKOrCreated(response)) {
                StatusLine status = response.getStatusLine();
                LOGGER.info(String.format("%s %s %s", errorLog, status.getReasonPhrase(), status.getStatusCode()));
                return false;
            }
            HttpEntity responseEntity = response.getEntity();
            EntityUtils.consume(responseEntity);
            LOGGER.info(successLog);
            return true;
        } catch (IOException ex) {
            LOGGER.severe(String.format(errorLog + " %s", ex.getMessage()));
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException ex) {
                LOGGER.severe(ex.getMessage());
            }
        }
        return false;
    }

    private boolean isOKOrCreated(CloseableHttpResponse response) {
        return response.getStatusLine().getStatusCode() == 200
                || response.getStatusLine().getStatusCode() == 201;
    }

    private CloseableHttpClient getHTTPClient() {
        HttpHost target = new HttpHost(JCR_HOST, JCR_PORT, JCR_PROTOCOL);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials(JCR_USER_NAME, JCR_PASSWORD));
        return HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
    }

    public void post() {
        try {
            HttpHost target = new HttpHost("localhost", 8080, "http");
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(target.getHostName(), target.getPort()),
                    new UsernamePasswordCredentials("jhuska", "jhuska"));
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider).build();

            HttpPost httpPost = new HttpPost("http://localhost:8080/modeshape-rest/screenshots/default/items/fallout");
            StringEntity myEntity = new StringEntity("{\n"
                    + "    \"jcr:primaryType\":\"nt:unstructured\",\n"
                    + "    \"testProperty\":\"testValue\",\n"
                    + "    \"multiValuedProperty\":[\"value1\", \"value2\"],\n"
                    + "}",
                    ContentType.create("application/json", "UTF-8"));
            httpPost.setEntity(myEntity);
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void get() {
        try {
            HttpHost target = new HttpHost("localhost", 8080, "http");
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(target.getHostName(), target.getPort()),
                    new UsernamePasswordCredentials("jhuska", "jhuska"));
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider).build();
            HttpGet httpGet = new HttpGet("http://localhost:8080/modeshape-rest/screenshots");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                System.out.println("################ " + response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(entity1.getContent()));
                System.out.println(bfr.read());
                EntityUtils.consume(entity1);
            } finally {
                response1.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
