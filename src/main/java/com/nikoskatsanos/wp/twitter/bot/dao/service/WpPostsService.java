package com.nikoskatsanos.wp.twitter.bot.dao.service;

import com.nikoskatsanos.wp.twitter.bot.dao.WpBlogPost;

import java.util.Collection;

/**
 * <p>A service for interacting with a <b><a href="https://wordpress.com">Wordpress</a></b> database</p>
 *
 * @author nikkatsa
 */
public interface WpPostsService {

    /**
     * @return All {@link com.nikoskatsanos.wp.twitter.bot.dao.WpBlogPost}s found in the database
     */
    Collection<WpBlogPost> getAllPosts();
}
