package org.arquillian.graphene.visual.testing.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.arquillian.graphene.visual.testing.configuration.GrapheneVisualTestingConfiguration;
import org.arquillian.recorder.reporter.ReporterConfiguration;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.rusheye.arquillian.event.ComparisonEvent;
import org.jboss.rusheye.arquillian.event.CrawlEvent;

public class AfterSuiteListener {

    @Inject
    private Instance<GrapheneVisualTestingConfiguration> visualTestingConfiguration;

    @Inject
    private Instance<ReporterConfiguration> reporterConfiguration;

    @Inject
    private Event<ComparisonEvent> comparisonEvent;
    
    @Inject
    private Event<CrawlEvent> crawlEvent;

    public void listenToAfterSuite(@Observes AfterSuite event) {
        if(visualTestingConfiguration.get().isFirstRun()) {
            crawlEvent.fire(new CrawlEvent());
        } else {
            comparisonEvent.fire(new ComparisonEvent());
        }
//            copyNewScreenshotsToRepository();
    }

    private void copyNewScreenshotsToRepository() {
        Timestamp thisRunScreenshotsTimestamp = new Timestamp(System.currentTimeMillis());

        File destForNewScreenshots = new File(
                visualTestingConfiguration.get().getScreenshotsRepository().getAbsolutePath()
                + System.getProperty("file.separator")
                + thisRunScreenshotsTimestamp.toString().replaceAll("\\s", "_"));
        destForNewScreenshots.mkdir();
        try {
            FileUtils.copyDirectory(reporterConfiguration.get().getRootDir(), destForNewScreenshots);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
