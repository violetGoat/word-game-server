package com.violetgoat.wordgameserver;

import com.violetgoat.wordgameserver.dao.JdbcWordEntryDao;
import com.violetgoat.wordgameserver.dao.WordEntryDao;
import com.violetgoat.wordgameserver.service.WordListFileParser;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class WordListPopulator {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/wordlist";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PW = "postgres1";

    private static final String COMMON_WORD_LIST = "word-list-10000-proper-nouns-removed.txt";
    private static final String NOT_COMMON_WORD_LIST = "word-list-100000.txt";

    public static void main(String[] args) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(DB_URL);
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("postgres1");
        WordEntryDao wordEntryDao = new JdbcWordEntryDao(new JdbcTemplate(basicDataSource));
        WordListFileParser wordListFileParser = new WordListFileParser(wordEntryDao);
        wordListFileParser.populateTable(NOT_COMMON_WORD_LIST, "not-common");
        wordListFileParser.populateTable(COMMON_WORD_LIST, "common");
    }
}
