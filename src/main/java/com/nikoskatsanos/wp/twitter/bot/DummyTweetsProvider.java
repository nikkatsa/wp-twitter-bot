package com.nikoskatsanos.wp.twitter.bot;

import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>A dummy implementation of {@link com.nikoskatsanos.wp.twitter.bot.TweetsProvider} providing the current local time</p>
 *
 * @author nikkatsa
 */
public class DummyTweetsProvider implements TweetsProvider {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:SSS");

    @Override
    public TweetDTO getTweet() {
        return new TweetDTO(LocalDateTime.now().format(formatter), Lists.newArrayList("time", "now"));
    }
}
