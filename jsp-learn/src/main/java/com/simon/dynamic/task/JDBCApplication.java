package com.simon.dynamic.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JDBCApplication implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(JDBCApplication.class);

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public void run(String... strings) throws Exception {

    log.info("Creating tables");

    jdbcTemplate.execute("DROP TABLE artists IF EXISTS");
    jdbcTemplate.execute("CREATE TABLE artists (" + "`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
        + "`name` varchar(50) NOT NULL," + "`city` varchar(50) NOT NULL," + "`state` varchar(50) NOT NULL,"
        + "`phone` varchar(50) NOT NULL," + "`website` varchar(50) NOT NULL," + "`genres` varchar(50) NOT NULL);");

    jdbcTemplate.execute("DROP TABLE venues IF EXISTS");
    jdbcTemplate.execute(
        "CREATE TABLE venues (" + "`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY," + "`name` varchar(50) NOT NULL,"
            + "`address` varchar(50) NOT NULL," + "`city` varchar(50) NOT NULL," + "`state` varchar(50) NOT NULL,"
            + "`phone` varchar(50) NOT NULL," + "`website` varchar(50) NOT NULL," + "`genres` varchar(50) NOT NULL);");

    jdbcTemplate.execute("DROP TABLE shows IF EXISTS");
    jdbcTemplate.execute("CREATE TABLE shows (" + "`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
        + "`artist` int NOT NULL," + "`venue` int NOT NULL," + "`time` varchar(50) NOT NULL);");

    jdbcTemplate.execute("ALTER TABLE shows ADD FOREIGN KEY (artist) REFERENCES artists(id);");

    jdbcTemplate.execute("ALTER TABLE shows ADD FOREIGN KEY (venue) REFERENCES venues(id);");

    jdbcTemplate.execute(
        "INSERT INTO artists (name,city,state,phone,website,genres) VALUES ('Bob Dylan','Arlington','Virginia','121231231','bob.com','Pop');");
    jdbcTemplate.execute(
        "INSERT INTO venues (name,address, city,state,phone,website,genres) VALUES ('Mao Live House','5th Avenue','Washington','DC','654242442','mao.com','Rock');");
  }
}