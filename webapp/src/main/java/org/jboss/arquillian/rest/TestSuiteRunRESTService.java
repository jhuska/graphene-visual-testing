package org.jboss.arquillian.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.arquillian.managers.TestSuiteManager;
import org.jboss.arquillian.managers.TestSuiteRunManager;
import org.jboss.arquillian.model.testSuite.TestSuiteRun;

/**
 *
 * @author jhuska
 */
@Path("/runs")
@RequestScoped
public class TestSuiteRunRESTService {
    
    @Inject
    private TestSuiteRunManager testSuiteRunManager;
    
    @Inject
    private TestSuiteManager testSuiteManager;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Long createTestSuiteRun(TestSuiteRun testSuiteRun) {
        testSuiteRun.setTestSuite(testSuiteManager.getTestSuite(testSuiteRun.getTestSuite().getName()));
        return testSuiteRunManager.createTestSuiteRun(testSuiteRun).getTestSuiteRunID();
    }
}
