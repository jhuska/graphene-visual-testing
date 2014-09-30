package org.jboss.arquillian.model.testSuite;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jhuska
*/
@Entity(name = "TEST_SUITE_RUN")
public class TestSuiteRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEST_SUITE_RUN_ID")
    private Long testSuiteRunID;
    
    @Column(name = "TEST_SUITE_RUN_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    
    private String projectRevision;
    
    private int numberOfFailedFunctionalTests;
    
    private int numberOfFailedComparisons;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEST_SUITE_ID")
    @JsonManagedReference
    private TestSuite testSuite;

    public long getTestSuiteRunID() {
        return testSuiteRunID;
    }

    public void setTestSuiteRunID(long testSuiteRunID) {
        this.testSuiteRunID = testSuiteRunID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getProjectRevision() {
        return projectRevision;
    }

    public void setProjectRevision(String projectRevision) {
        this.projectRevision = projectRevision;
    }

    public int getNumberOfFailedFunctionalTests() {
        return numberOfFailedFunctionalTests;
    }

    public void setNumberOfFailedFunctionalTests(int numberOfFailedFunctionalTests) {
        this.numberOfFailedFunctionalTests = numberOfFailedFunctionalTests;
    }

    public int getNumberOfFailedComparisons() {
        return numberOfFailedComparisons;
    }

    public void setNumberOfFailedComparisons(int numberOfFailedComparisons) {
        this.numberOfFailedComparisons = numberOfFailedComparisons;
    }

    public TestSuite getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(TestSuite testSuite) {
        this.testSuite = testSuite;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.timestamp);
        hash = 59 * hash + Objects.hashCode(this.projectRevision);
        hash = 59 * hash + this.numberOfFailedFunctionalTests;
        hash = 59 * hash + this.numberOfFailedComparisons;
        hash = 59 * hash + Objects.hashCode(this.testSuite);
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
        final TestSuiteRun other = (TestSuiteRun) obj;
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        if (!Objects.equals(this.projectRevision, other.projectRevision)) {
            return false;
        }
        if (this.numberOfFailedFunctionalTests != other.numberOfFailedFunctionalTests) {
            return false;
        }
        if (this.numberOfFailedComparisons != other.numberOfFailedComparisons) {
            return false;
        }
        if (!Objects.equals(this.testSuite, other.testSuite)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TestSuiteRun{" + "testSuiteRunID=" + testSuiteRunID + ", timestamp=" + timestamp + ", projectRevision=" + projectRevision + ", numberOfFailedFunctionalTests=" + numberOfFailedFunctionalTests + ", numberOfFailedComparisons=" + numberOfFailedComparisons + ", testSuite=" + testSuite + '}';
    }
}
