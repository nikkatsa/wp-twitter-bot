package com.nikoskatsanos.wp.twitter.bot;


import com.nikoskatsanos.wp.twitter.bot.dao.service.WpPostsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

/**
 * @author nikkatsa
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:h2-context.xml"})
public class TweetSchedulerTest {

    @Autowired
    private ApplicationContext applicationContext;

    private TweetScheduler tweetScheduler;
    private Twitter mockTwitter;
    private TwitterService twitterService;
    private WordpressTweetsProvider tweetsProvider;
    private WpPostsService wpPostsService;

    @Before
    public void setupTweetSchedulerTest() {
        this.wpPostsService = applicationContext.getBean(WpPostsService.class);

        final UserOperations userOperations = Mockito.mock(UserOperations.class);
        when(userOperations.getScreenName()).thenReturn("TwitterUser");
        final TimelineOperations timelineOperations = Mockito.mock(TimelineOperations.class);

        this.mockTwitter = Mockito.mock(Twitter.class);
        when(this.mockTwitter.userOperations()).thenReturn(userOperations);
        when(this.mockTwitter.timelineOperations()).thenReturn(timelineOperations);

        this.twitterService = Mockito.spy(new TwitterService());
        this.twitterService.setTwitter(this.mockTwitter);
        this.twitterService.setCanTweet(true);

        this.tweetsProvider = new WordpressTweetsProvider();
        this.tweetsProvider.setWpPostsService(this.wpPostsService);

        this.tweetScheduler = new TweetScheduler();
        this.tweetScheduler.setTweetPeriod(100L);
        this.tweetScheduler.setTweetPeriodTimeUnit(TimeUnit.MILLISECONDS);
        this.tweetScheduler.setTweetsProvider(this.tweetsProvider);
        this.tweetScheduler.setTwitterService(this.twitterService);

        this.twitterService.start();
        this.tweetsProvider.start();
        this.tweetScheduler.start();
    }

    @After
    public void tearDownTweetSchedulerTest() throws IOException {
        this.tweetScheduler.stop();
        this.twitterService.stop();
    }

    @Test
    public void testTweet() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1_000L);

        Mockito.verify(this.twitterService, atLeastOnce()).tweet(any());
    }
}