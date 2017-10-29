package com.nikoskatsanos.wp.twitter.bot.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>An {@link javax.persistence.Entity} object mapping <b><a href="https://wordpress.com">Wordpress</a></b> table <em>wp_terms</em></p> <p><b>NOTE:</b> This
 * is a not a complete mapping of the <em>wp_terms</em> table, but rather some of the columns</p>
 *
 * @author nikkatsa
 */
@Entity
@Table(name = "wp_terms")
public class WpTerms implements Serializable {
    private Long termId;
    private String name;
    private String slug;

    public WpTerms() {
    }

    public WpTerms(Long termId, String name, String slug) {
        this.termId = termId;
        this.name = name;
        this.slug = slug;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id", unique = true, nullable = false)
    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "slug", nullable = false, length = 200)
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
