package com.nikoskatsanos.wp.twitter.bot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author nikkatsa
 */
public class Application {

    public static void main(final String... args) {
        new ClassPathXmlApplicationContext("wp-twitter-bot.xml");
    }
}
