package org.arquillian.graphene.visual.testing.impl;

import java.util.logging.Logger;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.TestResult.Status;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.rusheye.arquillian.event.FailedTestsCollection;

/**
 *
 * @author jhuska
 */
public class AfterListener {

    private static final Logger LOGGER = Logger.getLogger(AfterListener.class.getName());
    
    @Inject
    private Instance<FailedTestsCollection> failedTestsCollection;
    
    public void listenTo(@Observes After event, TestResult result) {
        if(result.getStatus() == Status.FAILED) {
           failedTestsCollection.get()
                   .addTest(event.getTestClass().getName() + "." + event.getTestMethod().getName());
        }
    }
}
