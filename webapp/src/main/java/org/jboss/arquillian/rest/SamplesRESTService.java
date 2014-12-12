package org.jboss.arquillian.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.arquillian.managers.SampleManager;
import org.jboss.arquillian.managers.TestSuiteRunManager;
import org.jboss.arquillian.model.testSuite.Pattern;
import org.jboss.arquillian.model.testSuite.Sample;
import org.jboss.arquillian.model.testSuite.TestSuiteRun;

/**
 *
 * @author jhuska
 */
@Path("/samples")
@RequestScoped
public class SamplesRESTService {
    
    @Inject
    private TestSuiteRunManager testSuiteRunManager;
    
    @Inject
    private SampleManager sampleManager;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Long createSample(Sample sample) {
        TestSuiteRun testSuiteRun = testSuiteRunManager.findById(sample.getSampleID());
        Sample toCreate = new Sample();
        toCreate.setName(sample.getName());
        toCreate.setTestSuiteRun(testSuiteRun);
        toCreate.setUrlOfScreenshot(sample.getUrlOfScreenshot());
        return sampleManager.createTestSuiteRun(toCreate).getSampleID();
    }
}
