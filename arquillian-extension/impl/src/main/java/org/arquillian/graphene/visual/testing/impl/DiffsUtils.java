package org.arquillian.graphene.visual.testing.impl;

/**
 *
 * @author jhuska
 */
public class DiffsUtils {

    private boolean diffCreated;

    public DiffsUtils() {
    }

    public DiffsUtils(boolean diffCreated) {
        this.diffCreated = diffCreated;
    }

    public boolean isDiffCreated() {
        return diffCreated;
    }

    public void setDiffCreated(boolean diffCreated) {
        this.diffCreated = diffCreated;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.diffCreated ? 1 : 0);
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
        final DiffsUtils other = (DiffsUtils) obj;
        if (this.diffCreated != other.diffCreated) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DiffsUtils{" + "diffCreated=" + diffCreated + '}';
    }
}
