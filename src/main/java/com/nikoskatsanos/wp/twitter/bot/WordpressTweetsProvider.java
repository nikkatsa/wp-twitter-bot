package com.nikoskatsanos.wp.twitter.bot;

import com.nikoskatsanos.jutils.core.threading.NamedThreadFactory;
import com.nikoskatsanos.nkjutils.yalf.YalfLogger;
import com.nikoskatsanos.wp.twitter.bot.dao.WpBlogPost;
import com.nikoskatsanos.wp.twitter.bot.dao.service.WpPostsService;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author nikkatsa
 */
public class WordpressTweetsProvider implements TweetsProvider {
    private static final YalfLogger log = YalfLogger.getLogger(WordpressTweetsProvider.class);

    private WpPostsService wpPostsService;

    private volatile Future<Collection<WpBlogPost>> wpBlogPostsFuture;

    private Deque<WpBlogPost> wpBlogPostsQueue;

    public WordpressTweetsProvider() {
        this.wpBlogPostsQueue = new ArrayDeque<>(50);
    }

    @PostConstruct
    public void start() {
        log.info("Starting %s", this.getClass().getSimpleName());

        this.refreshBlogPosts();
    }

    private void refreshBlogPosts() {
        this.wpBlogPostsFuture = Executors.newSingleThreadExecutor(new NamedThreadFactory("WpBlogPostRetriever", true)).submit(new Callable<Collection<WpBlogPost>>() {
            @Override
            public Collection<WpBlogPost> call() throws Exception {
                final Collection<WpBlogPost> blogPosts = wpPostsService.getAllPosts();
                log.info("%d Wordpress blog posts found", blogPosts.size());
                return blogPosts;
            }
        });

        try {
            final Collection<WpBlogPost> wpBlogPosts = this.wpBlogPostsFuture.get(5_000, TimeUnit.MILLISECONDS);
            if (wpBlogPosts.size() > 0) {
                this.wpBlogPostsQueue.clear();
                this.wpBlogPostsQueue.addAll(wpBlogPosts);
            }
        } catch (final InterruptedException | ExecutionException | TimeoutException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public TweetDTO getTweet() {
        if (this.wpBlogPostsQueue.size() == 0) {
            log.info("No available Wordpress Blog Posts found. Try to retrieve from data source");
            this.refreshBlogPosts();
        }

        final Optional<WpBlogPost> wpBlogPost = Optional.ofNullable(this.wpBlogPostsQueue.pollFirst());
        if (wpBlogPost.isPresent()) {
            return new TweetDTO(String.format("%s - %s", wpBlogPost.get().getTitle(), wpBlogPost.get().getUrl().toString()), wpBlogPost.get().getTags());
        }
        throw new RuntimeException("No posts found");
    }

    @Required
    public void setWpPostsService(final WpPostsService wpPostsService) {
        this.wpPostsService = wpPostsService;
    }
}
