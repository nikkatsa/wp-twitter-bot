package com.nikoskatsanos.wp.twitter.bot.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * <p>An {@link javax.persistence.Entity} object mapping <b><a href="https://wordpress.com">Wordpress</a></b> table <em>wp_term_relationships</em></p>
 * <p><b>NOTE:</b> This is a not a complete mapping of the <em>wp_term_relationships</em> table, but rather some of the columns</p>
 *
 * @author nikkatsa
 */
@Entity
@Table(name = "wp_term_relationships")
public class WpTermRelationships implements Serializable {
    private Long objectId;
    private Long termTaxonomyId;

    private Set<WpTermsTaxonomy> termTaxonomies;

    public WpTermRelationships() {
    }

    public WpTermRelationships(Long objectId, Long termTaxonomyId, Set<WpTermsTaxonomy> termTaxonomies) {
        this.objectId = objectId;
        this.termTaxonomyId = termTaxonomyId;
        this.termTaxonomies = termTaxonomies;
    }

    @Column(name = "object_id", nullable = false)
    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_taxonomy_id", unique = true, nullable = false)
    public Long getTermTaxonomyId() {
        return termTaxonomyId;
    }

    public void setTermTaxonomyId(Long termTaxonomyId) {
        this.termTaxonomyId = termTaxonomyId;
    }

    @OneToMany(targetEntity = WpTermsTaxonomy.class, mappedBy = "termTaxonomyId")
    public Set<WpTermsTaxonomy> getTermTaxonomies() {
        return termTaxonomies;
    }

    public void setTermTaxonomies(Set<WpTermsTaxonomy> termTaxonomies) {
        this.termTaxonomies = termTaxonomies;
    }
}
