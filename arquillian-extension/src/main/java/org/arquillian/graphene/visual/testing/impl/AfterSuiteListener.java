package org.arquillian.graphene.visual.testing.impl;

import org.arquillian.extension.recorder.screenshooter.ScreenshooterConfiguration;
import org.arquillian.graphene.visual.testing.api.DescriptorAndPatternsHandler;
import org.arquillian.graphene.visual.testing.configuration.GrapheneVisualTestingConfiguration;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.rusheye.arquillian.event.StartParsingEvent;
import org.jboss.rusheye.arquillian.event.StartCrawlinglEvent;
import java.util.logging.Logger;
import org.jboss.rusheye.arquillian.event.FailedTestsCollection;

public class AfterSuiteListener {

    @Inject
    private Instance<GrapheneVisualTestingConfiguration> visualTestingConfiguration;

    @Inject
    private Instance<ScreenshooterConfiguration> screenshooterConfiguration;

    @Inject
    private Event<StartParsingEvent> startParsingEvent;
    
    @Inject
    private Event<StartCrawlinglEvent> crawlEvent;

    @Inject
    private Instance<ServiceLoader> serviceLoader;
    
    @Inject
    private Instance<FailedTestsCollection> failedTestsCollection;
    
    private static final Logger LOGGER = Logger.getLogger(AfterSuiteListener.class.getName());

    public void listenToAfterSuite(@Observes AfterSuite event) {
        String samplesPath = screenshooterConfiguration.get().getRootDir().getAbsolutePath();
        if (visualTestingConfiguration.get().isFirstRun()) {
            crawlEvent.fire(new StartCrawlinglEvent(samplesPath));
        } else {
            String descriptorAndPatternsDir
                    = serviceLoader.get().onlyOne(DescriptorAndPatternsHandler.class).retrieveDescriptorAndPatterns();
            startParsingEvent.fire(new StartParsingEvent(descriptorAndPatternsDir, samplesPath, failedTestsCollection.get()));
        }
    }
}
