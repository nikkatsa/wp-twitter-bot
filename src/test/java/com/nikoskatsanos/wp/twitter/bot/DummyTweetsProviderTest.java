package com.nikoskatsanos.wp.twitter.bot;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author nikkatsa
 */
public class DummyTweetsProviderTest {

    private TweetsProvider tweetsProvider;

    @Before
    public void setupDummyTweetsProviderTest(){
        this.tweetsProvider = new DummyTweetsProvider();
    }

    @Test
    public void testGetTweet() {
        final TweetDTO tweet = this.tweetsProvider.getTweet();
        assertNotNull(tweet.getMsg());

        final List<String> hashTags = new ArrayList<>(tweet.getHashTags());
        assertEquals(2, hashTags.size());
        assertEquals("time", hashTags.get(0));
        assertEquals("now", hashTags.get(1));
    }
}