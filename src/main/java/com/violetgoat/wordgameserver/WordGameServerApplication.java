package com.violetgoat.wordgameserver;

import com.violetgoat.wordgameserver.dao.JdbcWordEntryDao;
import com.violetgoat.wordgameserver.dao.WordEntryDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class WordGameServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordGameServerApplication.class, args);
	}

}
