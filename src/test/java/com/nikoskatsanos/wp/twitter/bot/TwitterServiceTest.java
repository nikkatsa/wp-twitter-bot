package com.nikoskatsanos.wp.twitter.bot;

import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.UserOperations;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author nikkatsa
 */
public class TwitterServiceTest {

    private TimelineOperations timelineOperations;
    private Twitter twitter;
    private TwitterService twitterService;

    @Before
    public void setupTwitterServiceTest() {
        final UserOperations userOperations = Mockito.mock(UserOperations.class);
        when(userOperations.getScreenName()).thenReturn("TwitterUser");

        this.timelineOperations = Mockito.mock(TimelineOperations.class);

        this.twitter = Mockito.mock(Twitter.class);
        when(this.twitter.userOperations()).thenReturn(userOperations);
        when(this.twitter.timelineOperations()).thenReturn(this.timelineOperations);

        this.twitterService = new TwitterService();
        this.twitterService.setCanTweet(true);
        this.twitterService.setTwitter(this.twitter);

        this.twitterService.start();
    }

    @After
    public void tearDownTwitterServiceTest() throws IOException {
        this.twitterService.stop();
    }

    @Test
    public void testTweet() {
        assertEquals(true, this.twitterService.isCanTweet());

        this.twitterService.tweet(new TweetDTO("Hello World", Sets.newHashSet("hello", "world")));

        verify(this.timelineOperations, times(1)).updateStatus(any(TweetData.class));
    }

    @Test
    public void testTweet_canTweetFalse() {
        this.twitterService.setCanTweet(false);

        assertEquals(false, this.twitterService.isCanTweet());

        this.twitterService.tweet(new TweetDTO("Hello World", Sets.newHashSet("hello", "world")));

        verify(this.timelineOperations, Mockito.never()).updateStatus(any(TweetData.class));
    }
}