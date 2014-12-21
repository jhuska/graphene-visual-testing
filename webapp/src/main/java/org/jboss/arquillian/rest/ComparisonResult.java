package org.jboss.arquillian.rest;

import java.io.Serializable;

/**
 *
 * @author jhuska
 */
public class ComparisonResult implements Serializable {
    
    private String patternUrl;
    
    private Long patternID;
    
    private String sampleUrl;
    
    private Long sampleID;
    
    private String diffUrl;
    
    private Long diffID;
    
    private String testClassName;
    
    private String testName;

    public ComparisonResult() {
    }

    public ComparisonResult(String patternUrl, Long patternID, String sampleUrl, Long sampleID, 
            String diffUrl, Long diffID, String testClassName, String testName) {
        this.patternUrl = patternUrl;
        this.patternID = patternID;
        this.sampleUrl = sampleUrl;
        this.sampleID = sampleID;
        this.diffUrl = diffUrl;
        this.diffID = diffID;
        this.testClassName = testClassName;
        this.testName = testName;
    }

    public String getPatternUrl() {
        return patternUrl;
    }

    public void setPatternUrl(String patternUrl) {
        this.patternUrl = patternUrl;
    }

    public Long getPatternID() {
        return patternID;
    }

    public void setPatternID(Long patternID) {
        this.patternID = patternID;
    }

    public String getSampleUrl() {
        return sampleUrl;
    }

    public void setSampleUrl(String sampleUrl) {
        this.sampleUrl = sampleUrl;
    }

    public Long getSampleID() {
        return sampleID;
    }

    public void setSampleID(Long sampleID) {
        this.sampleID = sampleID;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    public Long getDiffID() {
        return diffID;
    }

    public void setDiffID(Long diffID) {
        this.diffID = diffID;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
