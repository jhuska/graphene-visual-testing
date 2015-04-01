package org.arquillian.graphene.visual.testing.api;

import org.jboss.rusheye.arquillian.event.ParsingDoneEvent;

/**
 *
 * @author jhuska
 */
public interface SamplesAndDiffsHandler {

    /**
     * Persist samples and created diffs for a later usage.
     * 
     * @param parsingDoneEvent event fired by Rusheye when parsing of screenshots is done. So samples and diffs can be
     * created
     */
    void saveSamplesAndDiffs(ParsingDoneEvent parsingDoneEvent);
}
