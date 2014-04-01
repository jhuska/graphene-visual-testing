package org.arquillian.graphene.visual.testing.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.arquillian.graphene.visual.testing.configuration.GrapheneVisualTestingConfiguration;
import org.arquillian.recorder.reporter.ReporterConfiguration;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.xml.sax.SAXException;

public class ScreenshotComparisonMaker {

    @Inject
    private Instance<GrapheneVisualTestingConfiguration> visualTestingConfHolder;

    @Inject
    private Instance<ReporterConfiguration> reporterConfigurationHolder;

    private static final String DESCRIPTOR_NAME = "suite.xml";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public void makeComparison(@Observes MakeScreenshotsComparisonEvent event) {
        crawlPatternsDirectory(visualTestingConfHolder.get().getLastValidScreenshots());
    }

    private void crawlPatternsDirectory(File patternsDir) {
        boolean comparisonWasDone = false;
        for (File f : patternsDir.listFiles()) {
            if (f.isDirectory()) {
                crawlPatternsDirectory(f);
            }
            if (Arrays.asList(patternsDir.list()).contains(DESCRIPTOR_NAME) && !comparisonWasDone) {
                doComparison(
                        new File(patternsDir.getAbsolutePath() + FILE_SEPARATOR + DESCRIPTOR_NAME), 
                        patternsDir,
                        getSamplesDirFromActualPatternDir(f)
                        );
                comparisonWasDone = true;
            }
        }
    }
    
    private File getSamplesDirFromActualPatternDir(File actualPatternsDir) {
        //removing the root of the path
        String patternSubFolder = actualPatternsDir.getAbsolutePath()
                .replace(visualTestingConfHolder.get().getLastValidScreenshots().getAbsolutePath(), "");
        //removing the actual screnshot file from path
        patternSubFolder = patternSubFolder.substring(0, patternSubFolder.lastIndexOf(FILE_SEPARATOR) + 1);
        //concating the absolute path of samples with relative subfolder of patterns
        return new File(reporterConfigurationHolder.get().getRootDir().getAbsolutePath().concat(patternSubFolder));
    }

    private void doComparison(File descriptor, File patterns, File samples) {
        GrapheneVisualTestingConfiguration visualTestingConf = visualTestingConfHolder.get();
        String[] args = { 
                "parse", 
                descriptor.getAbsolutePath(), 
                "-D" ,"result-output-file=" + visualTestingConf.getResult().toString(),
                "-D" ,"samples-directory=" + samples.getAbsolutePath(),
                "-D", "patterns-directory=" + patterns.getAbsolutePath(),
                "-D", "file-storage-directory=" + visualTestingConf.getDiffs().getAbsolutePath()
                };
        try {
            org.jboss.rusheye.Main.main(args);
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}