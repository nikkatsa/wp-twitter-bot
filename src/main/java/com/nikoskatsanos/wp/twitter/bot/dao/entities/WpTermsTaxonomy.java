package com.nikoskatsanos.wp.twitter.bot.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>An {@link javax.persistence.Entity} object mapping <b><a href="https://wordpress.com">Wordpress</a></b> table <em>wp_term_taxonomy</em></p>
 * <p><b>NOTE:</b> This is a not a complete mapping of the <em>wp_term_taxonomy</em> table, but rather some of the columns</p>
 *
 * @author nikkatsa
 */
@Entity
@Table(name = "wp_term_taxonomy")
public class WpTermsTaxonomy implements Serializable {

    private Long termTaxonomyId;
    private Long termId;
    private WpTerms wpTerm;
    private String taxonomy;

    public WpTermsTaxonomy() {
    }

    public WpTermsTaxonomy(Long termTaxonomyId, Long termId, WpTerms wpTerm, String taxonomy) {
        this.termTaxonomyId = termTaxonomyId;
        this.termId = termId;
        this.wpTerm = wpTerm;
        this.taxonomy = taxonomy;
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

    @Column(name = "term_id", nullable = false, insertable = false, updatable = false)
    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    @ManyToOne(targetEntity = WpTerms.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "term_id")
    public WpTerms getWpTerm() {
        return wpTerm;
    }

    public void setWpTerm(WpTerms wpTerm) {
        this.wpTerm = wpTerm;
    }

    @Column(name = "taxonomy", nullable = false, length = 32)
    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }
}
