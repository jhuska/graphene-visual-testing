package sk.najkrajsie.videoApp.model.resources;

public enum MediaType {

    /**
     * The types of media the application can currently handle. Right now, it can only handle images. We plan to add support for
     * streamed videos in the next development round.
     */
    IMAGE("Image", true),    
    VIDEO("Video", true);
    
    /**
     * A human readable description of the media type.
     */
    private final String description;
    
    /**
     * A boolean flag indicating whether the media type can be cached.
     */
    private final boolean cacheable;
    
    /* Boilerplate constructor and getters */

    private MediaType(String description, boolean cacheable) {
        this.description = description;
        this.cacheable = cacheable;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCacheable() {
        return cacheable;
    }
}