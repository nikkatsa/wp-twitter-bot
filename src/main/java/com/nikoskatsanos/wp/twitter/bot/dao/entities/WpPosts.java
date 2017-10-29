package com.nikoskatsanos.wp.twitter.bot.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * <p>An {@link javax.persistence.Entity} object mapping <b><a href="https://wordpress.com">Wordpress</a></b> table <em>wp_posts</em></p> <p><b>NOTE:</b> This
 * is a not a complete mapping of the <em>wp_posts</em> table, but rather some of the columns</p>
 *
 * @author nikkatsa
 */
@Entity
@Table(name = "wp_posts")
public class WpPosts implements Serializable {

    private Long id;
    private String postTitle;
    private String postType;
    private String guid;
    private Set<WpTermRelationships> termRelationships;

    public WpPosts() {
    }

    public WpPosts(Long id, String postTitle, String postType, String guid, Set<WpTermRelationships> termRelationships) {
        this.id = id;
        this.postTitle = postTitle;
        this.postType = postType;
        this.guid = guid;
        this.termRelationships = termRelationships;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "post_title", nullable = false, length = 65535)
    public String getPostTitle() {
        return this.postTitle;
    }

    public void setPostTitle(final String postTitle) {
        this.postTitle = postTitle;
    }

    @Column(name = "post_type", nullable = false, length = 65535)
    public String getPostType() {
        return this.postType;
    }

    public void setPostType(final String postType) {
        this.postType = postType;
    }

    @Column(name = "guid", nullable = false)
    public String getGuid() {
        return this.guid;
    }

    public void setGuid(final String guid) {
        this.guid = guid;
    }

    @OneToMany(targetEntity = WpTermRelationships.class, mappedBy = "objectId")
    public Set<WpTermRelationships> getTermRelationships() {
        return termRelationships;
    }

    public void setTermRelationships(Set<WpTermRelationships> termRelationships) {
        this.termRelationships = termRelationships;
    }
}

