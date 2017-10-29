package com.nikoskatsanos.wp.twitter.bot;

import com.google.common.base.MoreObjects;
import org.springframework.social.twitter.api.TweetData;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p>Simple POJO describing a tweet</p>
 *
 * @author nikkatsa
 */
public class TweetDTO implements Serializable {

    private final String msg;
    private final Collection<String> hashTags;

    public TweetDTO(final String msg, final Collection<String> hashTags) {
        this.msg = msg;
        this.hashTags = hashTags;
    }

    public String getMsg() {
        return this.msg;
    }

    public Collection<String> getHashTags() {
        return this.hashTags;
    }

    public TweetData toTweet() {
        return new TweetData(this.toTweetStr());
    }

    public String toTweetStr() {
        return String.format("%s %s", this.msg, this.hashTags.stream().map(tag -> String.format("#%s", tag)).collect(Collectors.joining(" "))).trim();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("msg", this.msg).add("hashTags", this.hashTags.stream().distinct().map(tag -> String.format("#%s", tag))
                .collect(Collectors.joining(" "))).toString();
    }
}
