package com.nikoskatsanos.wp.twitter.bot;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author nikkatsa
 */
public class TweetDTOTest {

    @Test
    public void testTweetDTO() {
        final TweetDTO tweetDTO = new TweetDTO("Hello World", Sets.newHashSet("hello", "world"));
        assertEquals("Hello World #hello #world", tweetDTO.toTweetStr());
        assertNotNull(tweetDTO.toTweet());

        final TweetDTO tweetDTONoHashTags = new TweetDTO("Hello World", Collections.emptyList());
        assertEquals("Hello World", tweetDTONoHashTags.toTweetStr());
        assertNotNull(tweetDTONoHashTags.toTweet());
    }
}