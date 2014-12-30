package org.arquillian.graphene.visual.testing.impl;

import java.util.logging.Logger;
import org.arquillian.extension.recorder.screenshooter.ScreenshooterConfiguration;
import org.arquillian.graphene.visual.testing.configuration.GrapheneVisualTestingConfiguration;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.api.event.ManagerStopping;

/**
 *
 * @author jhuska
 */
public class ManagerStoppingObserver {

    private Logger LOGGER = Logger.getLogger(ManagerStoppingObserver.class.getName());
    
    @Inject
    private Instance<GrapheneVisualTestingConfiguration> grapheneVisTesConf;
    
    @Inject
    private Instance<DiffsUtils> diffsUtils;
    
    public void observe(@Observes(precedence = Integer.MAX_VALUE) ManagerStopping event) {
        if(diffsUtils.get().isDiffCreated() && grapheneVisTesConf.get().isFailBuild()) {
            throw new RuntimeException("Visual Testing has failed! Set failBuild to false if "
                    + "you do not want to see this exception.");
        }
    }
}
