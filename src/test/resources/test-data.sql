INSERT INTO wp_posts VALUES( 1, 'Post1', 'post', 'https://nikoskatsanos.com/1' );
INSERT INTO wp_posts VALUES( 2, 'Post2', 'post', 'https://nikoskatsanos.com/2' );
INSERT INTO wp_posts VALUES( 3, 'Post3', 'post', 'https://nikoskatsanos.com/3' );
INSERT INTO wp_posts VALUES( 4, 'Post4', 'post', 'https://nikoskatsanos.com/4' );
INSERT INTO wp_posts VALUES( 5, 'Post5', 'post', 'https://nikoskatsanos.com/5' );

INSERT INTO wp_terms VALUES( 1, 'java', 'java');
INSERT INTO wp_terms VALUES( 2, 'Design Patterns', 'design-patterns');
INSERT INTO wp_terms VALUES( 3, 'OO', 'oo');
INSERT INTO wp_terms VALUES( 4, 'JShell', 'jshell');

INSERT INTO wp_term_taxonomy VALUES( 1, 1, 'post_tag');
INSERT INTO wp_term_taxonomy VALUES( 2, 2, 'post_tag');
INSERT INTO wp_term_taxonomy VALUES( 3, 3, 'post_tag');
INSERT INTO wp_term_taxonomy VALUES( 4, 4, 'post_tag');

INSERT INTO wp_term_relationships VALUES(1, 1);
INSERT INTO wp_term_relationships VALUES(1, 2);
INSERT INTO wp_term_relationships VALUES(1, 3);
INSERT INTO wp_term_relationships VALUES(2, 3);
INSERT INTO wp_term_relationships VALUES(2, 4);
INSERT INTO wp_term_relationships VALUES(3, 4);
INSERT INTO wp_term_relationships VALUES(4, 4);
INSERT INTO wp_term_relationships VALUES(5, 4);