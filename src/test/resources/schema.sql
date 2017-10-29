CREATE SCHEMA IF NOT EXISTS test_wordpress_db;
SET SCHEMA test_wordpress_db;

CREATE TABLE wp_posts (
  ID INTEGER PRIMARY KEY,
  post_title VARCHAR(255),
  post_type VARCHAR(255),
  guid VARCHAR(255)
);

CREATE TABLE wp_terms (
  term_id INTEGER PRIMARY KEY,
  name VARCHAR(255),
  slug VARCHAR(255)
);

CREATE TABLE wp_term_taxonomy (
    term_taxonomy_id INTEGER PRIMARY KEY,
    term_id INTEGER,
    taxonomy VARCHAR(255),
    FOREIGN KEY (term_id) REFERENCES wp_terms(term_id)
);

CREATE TABLE wp_term_relationships(
    object_id INTEGER,
    term_taxonomy_id INTEGER,
    FOREIGN KEY( term_taxonomy_id ) REFERENCES wp_term_taxonomy( term_taxonomy_id ),
    FOREIGN KEY( object_id ) REFERENCES wp_posts(ID)
)