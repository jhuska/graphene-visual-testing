package org.arquillian.graphene.visual.testing.impl.jaxbmodel;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jhuska
 */
@XmlRootElement(name = "visual-suite-result", namespace = "http://www.jboss.org/rusheye/visual-suite-result")
@XmlType(name = "VisualSuiteResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisualSuiteResult {
    
    /** The list of tests */
    @XmlElement(name = "test")
    private List<Test> tests;

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}