package com.nikoskatsanos.wp.twitter.bot.dao.service;

import com.nikoskatsanos.nkjutils.yalf.YalfLogger;
import com.nikoskatsanos.wp.twitter.bot.dao.WpBlogPost;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>Hibernate implementation of {@link com.nikoskatsanos.wp.twitter.bot.dao.service.WpPostsService}</p>
 *
 * @author nikkatsa
 */
@Repository
public class WpPostsServiceImpl implements WpPostsService {

    private static final YalfLogger log = YalfLogger.getLogger(WpPostsServiceImpl.class);

    private static final String HQL = "SELECT post.id, post.postTitle, post.guid, term.slug, term.name FROM WpPosts as post JOIN post.termRelationships as "
            + "termRelationships JOIN termRelationships.termTaxonomies as termTaxonomies JOIN termTaxonomies.wpTerm as term WHERE post.postType = 'post'";

    private static final Collector<Triplet<String, String, String>, List<String>, List<String>> WP_BLOG_POSTS_TAGS_COLLECTOR = Collector.of(ArrayList::new,
            (l, t) -> l.add(t.getValue2()), (l, r) -> l, Collector.Characteristics.IDENTITY_FINISH);

    private SessionFactory sessionFactory;

    @PostConstruct
    public void start() {
        log.info("Starting %s", this.getClass().getSimpleName());
    }

    @PreDestroy
    public void stop() {
        log.info("Stopping %s", this.getClass().getSimpleName());
        this.sessionFactory.close();
    }

    @Override
    public Collection<WpBlogPost> getAllPosts() {
        try (final Session session = this.sessionFactory.openSession()) {
            final List<?> results = session.createQuery(HQL).list();

            final Map<Pair<String, String>, List<String>> dbValues = results.stream().map(r -> this.toPostTitleURLTagTriplet(r)).collect(Collectors
                    .groupingBy(t -> Pair.with(t.getValue0(), t.getValue1()), WP_BLOG_POSTS_TAGS_COLLECTOR));
            final List<WpBlogPost> blogPosts = dbValues.entrySet().stream().map(e -> new WpBlogPost(e.getKey().getValue0(), this.toURLUnchecked(e.getKey()
                    .getValue1()), new HashSet<>(e.getValue()))).collect(Collectors.toList());

            return blogPosts;
        }
    }

    private Triplet<String, String, String> toPostTitleURLTagTriplet(final Object object) {
        final Object[] values = (Object[]) object;
        return Triplet.with(values[1].toString(), values[2].toString(), values[3].toString());
    }

    private URL toURLUnchecked(final String url) {
        try {
            return new URL(url);
        } catch (final MalformedURLException e) {
            final String msg = String.format("%s was not a valid URL", url);
            log.warn(msg, e);
            throw new IllegalArgumentException(msg, e);
        }
    }

    @Required
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
