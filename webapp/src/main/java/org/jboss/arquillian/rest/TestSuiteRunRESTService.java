package org.jboss.arquillian.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.jboss.arquillian.managers.TestSuiteRunManager;

/**
 *
 * @author jhuska
 */
@RequestScoped
public class TestSuiteRunRESTService {
    
    @Inject
    private TestSuiteRunManager testSuiteRunManager;
}
