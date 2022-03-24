package com.violetgoat.wordgameserver.dao;

import com.violetgoat.wordgameserver.exception.WordEntryNotFoundException;
import com.violetgoat.wordgameserver.model.WordEntry;

import java.util.List;

public interface WordEntryDao {

    List<WordEntry> getAll();
    WordEntry getWordEntryById(int id) throws WordEntryNotFoundException;
    WordEntry getWordEntryByWord(String word) throws WordEntryNotFoundException;
    void deleteWordEntry(int id) throws WordEntryNotFoundException;
    void deleteWordEntry(String word) throws WordEntryNotFoundException;
    WordEntry createWordEntry(String word, String usage) throws WordEntryNotFoundException;
    List<String> getAllWordsWithLengthAndUsage(int length, String usage);
    int batchCreateWordEntries(List<WordEntry> wordEntries);

}
