# wp-twitter-bot
This is a twitter bot which connects to a wordpress database, retrieves the blog posts and their tags and translates those to tweets. The bot is tweeting in a pre-set interval, the name of the blog post, its URL and the post's tags as twitter hash tags.

# Technologies
The application is using the below technologies:
- Java 8
- Maven
- Spring framework (https://spring.io)
- Hibernate framework (http://hibernate.org/)

In order for the bot to work, someone will have to add some application and hibernate specific properties, according to the user's twitter account and Wordpress database.

More specifically, the user needs to add two files.

## application.properties
A file called application.properties need to be added under __src/main/resources__. This is a properties file which contains the user's specific Twitter API secrets. A sample file is shown below:

```properties
# Twitter API properties
spring.social.twitter.appId=value
spring.social.twitter.appSecret=value
twitter.access.token=value
twitter.access.token.secret=value
```
In order to generate the Twitter APIs access token please take a look at: https://developer.twitter.com/en/docs/basics/authentication/guides/access-tokens

## hibernate.properties
This file should also be created by the user and placed at __src/main/resources__. The file contains the Hibernate properties in order to connect to user's Wordpress database. A sample file could be:

```properties
# WordpressDB properties
jdbcDriverClassName=com.mysql.jdbc.Driver

hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
hibernate.connection.driverClass=com.mysql.jdbc.Driver
hibernate.connection.url=jdbc:mysql://127.0.0.1:3306/
hibernate.connection.username=user
hibernate.connection.password=pass
hibernate.default_schema=test_db
hibernate.default_catalog=test_db
```

# Running the application
When the user has creatd the above two file the application be run by its main class 
```java
com.nikoskatsanos.wp.twitter.bot.Application
```

No properties are really needed. The application will run in dry run mode (no real tweet will be published) and it tweets with a period of 1hour.

The user can control the tweet period and if the application should really send a tweet (not dry run) or not by using the below two JVM properites:
- -DtweetPeriod=${milliseconds}
- -DcanTweet=(true|false)

The above JVM properties are used by the spring configuration file in __src/main/resources/wp-twitter-bot.xml__

# Executable shell script
The project can be build using Maven. Once build an appassembler application will be created under __target/wp-twitter-bot/bin__. This is a shell script along with the appropriate binaries to run the application

# TODO
A Dockerfile will soon be created