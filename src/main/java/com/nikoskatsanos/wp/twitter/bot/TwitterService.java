package com.nikoskatsanos.wp.twitter.bot;

import com.codahale.metrics.Counter;
import com.nikoskatsanos.nkjutils.synthetic.metrics.MetricsFactory;
import com.nikoskatsanos.nkjutils.yalf.YalfLogger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.social.twitter.api.Twitter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>TwitterService referencing a {@link org.springframework.social.twitter.api.Twitter} account, twitting on behalf of that account</p>
 *
 * @author nikkatsa
 */
public class TwitterService implements Closeable {

    private static final YalfLogger log = YalfLogger.getLogger(TwitterService.class);

    private Twitter twitter;

    private Counter tweetsCounter;

    private boolean canTweet = false;

    public TwitterService() {
        this.tweetsCounter = MetricsFactory.createCounter("tweets");
    }

    @PostConstruct
    public void start() {
        Objects.requireNonNull(this.twitter, "Twitter service cannot be null");
        log.info("Starting twitter service for %s", this.twitter.userOperations().getScreenName());
    }

    @PreDestroy
    public void stop() throws IOException {
        this.close();
    }

    public void tweet(final TweetDTO tweet) {
        log.info("About to tweet: %s", tweet);
        this.tweetsCounter.inc();
        if (this.canTweet) {
            this.twitter.timelineOperations().updateStatus(tweet.toTweet());
        }
    }

    @Override
    public void close() throws IOException {
    }

    @Required
    public void setTwitter(final Twitter twitter) {
        this.twitter = twitter;
    }

    public boolean isCanTweet() {
        return this.canTweet;
    }

    public void setCanTweet(final boolean canTweet) {
        this.canTweet = canTweet;
    }
}
