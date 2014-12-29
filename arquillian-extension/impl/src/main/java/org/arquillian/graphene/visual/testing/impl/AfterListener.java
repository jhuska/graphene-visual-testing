package org.arquillian.graphene.visual.testing.impl;

import java.util.logging.Logger;
import org.arquillian.graphene.visual.testing.api.VisuallyUnstable;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.TestResult.Status;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.rusheye.arquillian.event.FailedTestsCollection;
import org.jboss.rusheye.arquillian.event.VisuallyUnstableTestsCollection;

/**
 *
 * @author jhuska
 */
public class AfterListener {

    private static final Logger LOGGER = Logger.getLogger(AfterListener.class.getName());
    
    @Inject
    private Instance<FailedTestsCollection> failedTestsCollection;
    
    @Inject
    private Instance<VisuallyUnstableTestsCollection> visuallyUnstableTestsCollection;
    
    public void listenTo(@Observes After event, TestResult result) {
        if(checkWhetherItIsVisuallyUnstable(event)) {
            visuallyUnstableTestsCollection.get().addTest(getTestName(event));
        }
        if(result.getStatus() == Status.FAILED || result.getThrowable() != null
                || result.getStatus() == Status.SKIPPED) {
            failedTestsCollection.get()
                   .addTest(getTestName(event));
        }
    }
    
    private String getTestName(After event) {
        return event.getTestClass().getName() + "." + event.getTestMethod().getName();
    }
    
    private boolean checkWhetherItIsVisuallyUnstable(After event) {
        return event.getTestMethod().getAnnotation(VisuallyUnstable.class) != null
                ||
               event.getTestClass().getAnnotation(VisuallyUnstable.class) != null;
    }
}
