package org.jboss.arquillian.rest;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.arquillian.jcr.CDIController;
import org.jboss.arquillian.managers.TestSuiteManager;
import org.jboss.arquillian.managers.TestSuiteRunManager;
import org.jboss.arquillian.model.testSuite.TestSuite;
import org.jboss.arquillian.model.testSuite.TestSuiteRun;

/**
 *
 * @author jhuska
 */
@Path("/suites")
@RequestScoped
public class TestSuiteRESTService {
    
    @Inject
    private TestSuiteManager testSuiteManager;
    
    @Inject
    private TestSuiteRunManager testSuiteRunManager;
    
    @Inject
    private CDIController cdiController;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TestSuite> getAllTestSuites() {
        return testSuiteManager.getAllTestSuites();
    }
    
    @GET
    @Path("/{testSuiteID:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TestSuiteRun> getAllTestSuiteRuns(@PathParam("testSuiteID") long id) throws RepositoryException {
        List<TestSuiteRun> result = testSuiteRunManager.getAllTestSuiteRuns(id);
        cdiController.storeSomePictures2();
        return result;
    }
}
