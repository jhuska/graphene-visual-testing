package org.jboss.arquillian.model.testSuite;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author jhuska
 */
@Entity(name = "COMPARISON")
public class Comparison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPARISON_ID")
    private long comparisonID;
    
    private String patternURL;
    
    private String sampleURL;
    
    private String diffURL;
    
    private boolean resolved;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_SUITE_RUN_ID")
    @JsonManagedReference
    private TestSuiteRun testSuiteRun;

    public TestSuiteRun getTestSuiteRun() {
        return testSuiteRun;
    }

    public void setTestSuiteRun(TestSuiteRun testSuiteRun) {
        this.testSuiteRun = testSuiteRun;
    }

    public long getComparisonID() {
        return comparisonID;
    }

    public void setComparisonID(long comparisonID) {
        this.comparisonID = comparisonID;
    }

    public String getPatternURL() {
        return patternURL;
    }

    public void setPatternURL(String patternURL) {
        this.patternURL = patternURL;
    }

    public String getSampleURL() {
        return sampleURL;
    }

    public void setSampleURL(String sampleURL) {
        this.sampleURL = sampleURL;
    }

    public String getDiffURL() {
        return diffURL;
    }

    public void setDiffURL(String diffURL) {
        this.diffURL = diffURL;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comparison other = (Comparison) obj;
        if (!Objects.equals(this.patternURL, other.patternURL)) {
            return false;
        }
        if (!Objects.equals(this.sampleURL, other.sampleURL)) {
            return false;
        }
        if (!Objects.equals(this.diffURL, other.diffURL)) {
            return false;
        }
        if (this.resolved != other.resolved) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comparison{" + "comparisonID=" + comparisonID + ", patternURL=" + patternURL + ", sampleURL=" + sampleURL + ", diffURL=" + diffURL + ", resolved=" + resolved + '}';
    }
}
