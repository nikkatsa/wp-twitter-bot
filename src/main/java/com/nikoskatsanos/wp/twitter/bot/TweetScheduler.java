package com.nikoskatsanos.wp.twitter.bot;

import com.nikoskatsanos.jutils.core.threading.NamedThreadFactory;
import com.nikoskatsanos.nkjutils.yalf.YalfLogger;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author nikkatsa
 */
public class TweetScheduler {

    private static final YalfLogger log = YalfLogger.getLogger(TweetScheduler.class);

    private TweetsProvider tweetsProvider;
    private TwitterService twitterService;

    private ScheduledExecutorService scheduller;

    private long tweetPeriod = 60 * 60 * 1_000;
    private TimeUnit tweetPeriodTimeUnit = TimeUnit.MILLISECONDS;

    @PostConstruct
    public void start() {
        log.info("Starting %s with a tweet period of %dms", this.getClass().getSimpleName(), this.tweetPeriod);
        this.scheduller = Executors.newScheduledThreadPool(1, new NamedThreadFactory("TweetScheduler", false));
        this.scheduller.scheduleAtFixedRate(() -> this.twitterService.tweet(this.tweetsProvider.getTweet()), 0L, this.tweetPeriod, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void stop() {
        log.info("Stopping %s", this.getClass().getSimpleName());
        if (!this.scheduller.isShutdown()) {
            this.scheduller.shutdownNow();
        }
    }

    @Required
    public void setTweetsProvider(final TweetsProvider tweetsProvider) {
        this.tweetsProvider = tweetsProvider;
    }

    @Required
    public void setTwitterService(final TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    public long getTweetPeriod() {
        return this.tweetPeriod;
    }

    public void setTweetPeriod(final long tweetPeriod) {
        this.tweetPeriod = tweetPeriod;
    }

    public TimeUnit getTweetPeriodTimeUnit() {
        return tweetPeriodTimeUnit;
    }

    public void setTweetPeriodTimeUnit(final TimeUnit tweetPeriodTimeUnit) {
        this.tweetPeriodTimeUnit = tweetPeriodTimeUnit;
    }
}
