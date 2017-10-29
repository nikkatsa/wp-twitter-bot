package com.nikoskatsanos.wp.twitter.bot.dao;

import com.google.common.base.MoreObjects;

import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p>Simple POJO describing a blog post</p>
 *
 * @author nikkatsa
 */
public class WpBlogPost {

    private final String title;
    private final URL url;
    private final Collection<String> tags;

    public WpBlogPost(final String title, final URL url, final Collection<String> tags) {
        this.title = title;
        this.url = url;
        this.tags = tags;
    }

    public String getTitle() {
        return this.title;
    }

    public URL getUrl() {
        return this.url;
    }

    public Collection<String> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("title", this.title).add("url", this.url).add("tags", this.tags.stream().collect(Collectors.joining(","))
        ).toString();
    }
}
