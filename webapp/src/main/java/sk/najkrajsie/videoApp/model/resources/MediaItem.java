package sk.najkrajsie.videoApp.model.resources;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.hibernate.validator.constraints.URL;

@Entity(name = "MEDIA_ITEM")
public class MediaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Column(unique = true)
    @URL
    private String url;

    @ManyToMany
//    @JoinTable(
//            name = "MEDIA_ITEM_AND_CATEGORY",
//            joinColumns = {
//                @JoinColumn(name = "MEDIA_ITEM_ID", referencedColumnName = "ID")},
//            inverseJoinColumns = {
//                @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
