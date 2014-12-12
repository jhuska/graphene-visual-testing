package org.jboss.arquillian.managers;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.arquillian.model.testSuite.Sample;

/**
 *
 * @author jhuska
 */
@Stateless
public class SampleManager {
    
    @Inject
    private EntityManager em;
    
    public Sample createTestSuiteRun(Sample sample) {
        em.persist(sample);
        return sample;
    }
}
