package org.arquillian.graphene.visual.testing.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Logger;
import java.util.Set;
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
import org.json.JSONObject;

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

    private static final String PATTERNS_DEFAULT_DIR = "target/patterns";

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
        return crawlAndUploadPatterns(patternsRootDir, patternsRootDir.getName(), httpclient);
    }

    @Override
    public String retrieveDescriptorAndPatterns() {
        CloseableHttpClient httpClient = getHTTPClient();
        String suiteName = grapheneVisualTestingConf.get().getTestSuiteName();

        HttpGet getDescriptor = new HttpGet(JCR_REPOSITORY_URL + "/binary/" + suiteName + "/suite.xml/jcr%3acontent/jcr%3adata");
        createDir(PATTERNS_DEFAULT_DIR);
        executeGetAndSaveToFile(getDescriptor, httpClient, PATTERNS_DEFAULT_DIR + "/suite.xml",
                String.format("Suite descriptor for %s was retrieved.", suiteName),
                String.format("ERROR occurred while retrieving suite descriptor for %s", suiteName));

        HttpGet getAllChildren = new HttpGet(JCR_REPOSITORY_URL + "/items/" + suiteName + "?depth=-1");
        getAllChildren.addHeader("Accept", "application/json");
        JSONObject allSuiteChildren = new JSONObject(executeGet(getAllChildren, httpClient, "All children retrieved",
                "Error while retrieving all children"));
        JSONObject testClasses = allSuiteChildren.getJSONObject("children").getJSONObject("patterns").getJSONObject("children");

        findAndDownloadScreenshot(testClasses, suiteName, httpClient);

        return PATTERNS_DEFAULT_DIR;
    }

    private void findAndDownloadScreenshot(JSONObject testClasses, String suiteName, CloseableHttpClient httpClient) {
        StringBuilder builder = new StringBuilder();
        for (Object testClass : testClasses.keySet()) {
            builder.append(testClass.toString());
            JSONObject tests = testClasses.getJSONObject(testClass.toString()).getJSONObject("children");
            for (Object test : tests.keySet()) {
                builder = appendWrappedStringWithSeparator(builder, test.toString());
                File testDir = new File(PATTERNS_DEFAULT_DIR + File.separator + builder.toString());
                testDir.mkdirs();
                JSONObject screenshots = tests.getJSONObject(test.toString()).getJSONObject("children");
                for (Object screenshot : screenshots.keySet()) {
                    builder.append(screenshot.toString());
                    String screenURL = suiteName + "/"
                            + testClass.toString() + "/" + test.toString() + "/" + screenshot.toString();
                    LOGGER.info(screenURL);
                    HttpGet getScreenshot = new HttpGet(JCR_REPOSITORY_URL + "/binary/" + screenURL
                            + "/jcr%3acontent/jcr%3adata");
                    executeGetAndSaveToFile(getScreenshot, httpClient, testDir.getAbsolutePath() ,"Screenshot retrieved from URL: " + screenURL,
                            "Error while retrieving screenshot: " + screenURL);
                    builder = new StringBuilder();
                    builder.append(testClass.toString());
                    builder = appendWrappedStringWithSeparator(builder, test.toString());
                }
                builder = new StringBuilder();
                builder.append(testClass.toString());
            }
            builder = new StringBuilder();
        }
    }

    private StringBuilder appendWrappedStringWithSeparator(StringBuilder builder, String toBeWrapped) {
        builder.append(File.separator);
        builder.append(toBeWrapped);
        builder.append(File.separator);
        return builder;
    }

    private void createDir(String path) {
        File theDir = new File(path);

        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                throw new RuntimeException(se);
            }
        }
    }

    private boolean crawlAndUploadPatterns(File patternsDir, String rootOfPatterns, CloseableHttpClient httpClient) {
        boolean result = true;
        for (File dirOrFile : patternsDir.listFiles()) {
            if (dirOrFile.isDirectory()) {
                result = crawlAndUploadPatterns(dirOrFile, rootOfPatterns, httpClient);
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
            if (!result) {
                return result;
            }
        }
        return result;
    }

    private String executeGet(HttpGet httpGet, CloseableHttpClient httpclient, String successLog, String errorLog) {
        CloseableHttpResponse response = null;
        BufferedReader bfr = null;
        StringBuilder builder = new StringBuilder();
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != 200) {
                if (errorLog != null) {
                    LOGGER.severe(errorLog);
                }
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                bfr = new BufferedReader(new InputStreamReader(entity.getContent()));

                String line = bfr.readLine();
                while (line != null) {
                    builder.append(line);
                    line = bfr.readLine();
                }
                EntityUtils.consume(entity);
                if (successLog != null) {
                    LOGGER.info(successLog);
                }
            }
        } catch (IOException ex) {
            if (errorLog != null) {
                LOGGER.severe(String.format("%s %s", errorLog, ex.getMessage()));
            }
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
            }
        }
        return builder.toString();
    }

    private void executeGetAndSaveToFile(HttpGet httpGet, CloseableHttpClient httpclient, String pathToSaveResponse, String successLog, String errorLog) {
        CloseableHttpResponse response = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != 200) {
                if (errorLog != null) {
                    LOGGER.severe(errorLog);
                }
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                is = entity.getContent();
                os = new FileOutputStream(new File(pathToSaveResponse));

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
                EntityUtils.consume(entity);
                if (successLog != null) {
                    LOGGER.info(successLog);
                }
            }
        } catch (IOException ex) {
            if (errorLog != null) {
                LOGGER.severe(String.format("%s %s", errorLog, ex.getMessage()));
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                }
            }
        }
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
