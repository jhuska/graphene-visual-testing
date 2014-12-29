package org.arquillian.graphene.visual.testing.test;

import java.io.File;
import org.jboss.rusheye.arquillian.configuration.RusheyeConfiguration;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author jhuska
 */
public class JCRSamplesAndDiffsHandlerTest {
    
    @Test
    public void testSaveSamplesAndDiff() {
        RusheyeConfiguration rusheyeConf = mock(RusheyeConfiguration.class);
        when(rusheyeConf.getWorkingDirectory()).thenReturn(new File("target"));
        when(rusheyeConf.getDiffsDir()).thenReturn(new File("diffs"));
        when(rusheyeConf.getResultOutputFile()).thenReturn("result.xml");
    }
}
