package com.violetgoat.wordgameserver.dao;

import com.violetgoat.wordgameserver.exception.WordEntryNotFoundException;
import com.violetgoat.wordgameserver.model.WordEntry;
import com.violetgoat.wordgameserver.service.WordEntryBatchPreparedStatementSetter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JdbcWordEntryDao implements WordEntryDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcWordEntryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WordEntry> getAll() {
        List<WordEntry> wordEntries = new ArrayList<>();
        String sql = "SELECT id, word, usage FROM word_entry;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            WordEntry user = mapRowToWordEntry(results);
           wordEntries.add(user);
        }
        return wordEntries;
    }

    @Override
    public List<String> getAllWordsWithLengthAndUsage(int length, String usage) {
        String sql = "SELECT word FROM word_entry WHERE usage = ? AND CHAR_LENGTH(word) = ?";
        List<String> words = jdbcTemplate.queryForList(sql, String.class, usage, length);
        return words;
    }

    @Override
    public WordEntry getWordEntryById(int id) throws WordEntryNotFoundException {
        String sql = "SELECT id, word, usage FROM word_entry WHERE id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()){
            return mapRowToWordEntry(rowSet);
        }
        throw new WordEntryNotFoundException("WordEntry " + id + " was not found.");
    }

    @Override
    public WordEntry getWordEntryByWord(String word) throws WordEntryNotFoundException {
        String sql = "SELECT id, word, usage FROM word_entry WHERE word = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, word);
        if (rowSet.next()){
            return mapRowToWordEntry(rowSet);
        }
        throw new WordEntryNotFoundException("WordEntry " + word + " was not found.");
    }

    @Override
    public void deleteWordEntry(int id) throws WordEntryNotFoundException {
        String sql = "DELETE FROM word_entry " +
                "WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, id);

        if(rowsAffected == 0){
            throw new WordEntryNotFoundException();
        }
    }

    @Override
    public void deleteWordEntry(String word) throws WordEntryNotFoundException {
        String sql = "DELETE FROM word_entry " +
                "WHERE word = ?";

        int rowsAffected = jdbcTemplate.update(sql, word);

        if(rowsAffected == 0){
            throw new WordEntryNotFoundException();
        }
    }

    @Override
    public WordEntry createWordEntry(String word, String usage) throws WordEntryNotFoundException {
        String sql = "INSERT INTO word_entry (word, usage) VALUES (?, ?) " +
                "ON CONFLICT(word) DO " +
                "UPDATE SET usage=excluded.usage " +
                "RETURNING id;";
        Integer newId;
        try {
            newId = jdbcTemplate.queryForObject(sql, Integer.class, word, usage, word);
        } catch (DataAccessException e) {
            return null;
        }
        return getWordEntryById(newId);
    }

    public int batchCreateWordEntries(List<WordEntry> wordEntries){
        String sql = "INSERT INTO word_entry (word, usage) VALUES (?, ?) " +
                "ON CONFLICT(word) DO UPDATE SET usage = excluded.usage;";
        int[] updates =jdbcTemplate.batchUpdate(sql, new WordEntryBatchPreparedStatementSetter(wordEntries));
        return Arrays.stream(updates).sum();
    }

    private WordEntry mapRowToWordEntry(SqlRowSet rowSet) {
        return new WordEntry()
                .setId(rowSet.getInt("id"))
                .setWord(rowSet.getString("word"))
                .setUsage(rowSet.getString("usage"));
    }


}
