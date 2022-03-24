package com.violetgoat.wordgameserver.service;

import com.violetgoat.wordgameserver.model.WordEntry;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WordEntryBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

    private List<WordEntry> wordEntries;

        public WordEntryBatchPreparedStatementSetter(List<WordEntry> wordEntries) {
            super();
            this.wordEntries = wordEntries;
        }

        @Override
        public void setValues(PreparedStatement ps, int i) {

            try {
                WordEntry wordEntry = wordEntries.get(i);
                ps.setString(1, wordEntry.getWord());
                ps.setString(2, wordEntry.getUsage());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getBatchSize() {
            return wordEntries.size();
    }
}
