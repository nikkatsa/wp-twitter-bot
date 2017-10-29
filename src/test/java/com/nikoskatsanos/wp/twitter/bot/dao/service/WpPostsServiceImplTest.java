package com.nikoskatsanos.wp.twitter.bot.dao.service;

import com.google.common.collect.Lists;
import com.nikoskatsanos.nkjutils.yalf.YalfLogger;
import com.nikoskatsanos.wp.twitter.bot.dao.WpBlogPost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author nikkatsa
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Profile({"test"})
@ContextConfiguration(locations = {"classpath:h2-context.xml"})
public class WpPostsServiceImplTest {

    private static final YalfLogger log = YalfLogger.getLogger(WpPostsServiceImplTest.class);

    private static final Pattern WP_BLOG_TO_STRING_REGEX = Pattern.compile("^WpBlogPost\\{title=Post[1-5]{1},\\surl=https://nikoskatsanos.com/[1-5]{1}," +
            "\\stags=.*\\}$");

    private static final List<String> KNOWN_TAGS = Lists.newArrayList("java", "design-patterns", "oo", "jshell");

    @Autowired
    private ApplicationContext context;

    @Before
    public void setupWpPostsServiceImplTest() throws SQLException {
    }

    @After
    public void tearDownWpPostsServiceImplTest() {
    }

    @Test
    public void testGetAllPosts() {
        final WpPostsService wpPostsService = (WpPostsService) context.getBean("hibernateDAO");
        assertNotNull(wpPostsService);


        final Collection<WpBlogPost> allPosts = wpPostsService.getAllPosts();
        final List<WpBlogPost> sortedPosts = new ArrayList<>(allPosts);
        Collections.sort(sortedPosts, (p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));

        assertEquals(5, sortedPosts.size());

        this.assertBlogPosts(sortedPosts);
    }

    private void assertBlogPosts(final List<WpBlogPost> blogPosts) {
        for (int i = 0; i < blogPosts.size(); i++) {
            assertEquals(String.format("Post%d", i + 1), blogPosts.get(i).getTitle());
            assertEquals(String.format("https://nikoskatsanos.com/%d", i + 1), blogPosts.get(i).getUrl().toString());
            assertTrue(blogPosts.get(i).getTags().size() > 0);
            this.assertTags(blogPosts.get(i).getTags());
            assertTrue(WP_BLOG_TO_STRING_REGEX.matcher(blogPosts.get(i).toString()).matches());
        }
    }

    private void assertTags(final Collection<String> tags) {
        for (final String tag : tags) {
            assertTrue(KNOWN_TAGS.contains(tag));
        }
    }
}