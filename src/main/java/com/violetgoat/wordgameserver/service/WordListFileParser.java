package com.violetgoat.wordgameserver.service;

import com.violetgoat.wordgameserver.dao.WordEntryDao;
import com.violetgoat.wordgameserver.exception.WordEntryNotFoundException;
import com.violetgoat.wordgameserver.model.WordEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Component
public class WordListFileParser {


    // == fields ==

    private boolean endOfFileFlag = false;
    private int lineCount = 0;
    private WordEntryDao wordEntryDao;
    private static final String COMMON_WORD_LIST = "word-list-10000-proper-nouns-removed.txt";
    private static final String NOT_COMMON_WORD_LIST = "word-list-100000.txt";

    // == constructor ==
    public WordListFileParser(WordEntryDao wordEntryDao){
        this.wordEntryDao = wordEntryDao;
    }

    // == methods ==

    @Transactional
    public void populateTable(String wordlistFilePath, String usage) {

            try {
                FileReader fileReader = new FileReader(wordlistFilePath);
                BufferedReader in = new BufferedReader(fileReader);

                int numEntries = readFile(in, usage);
                System.out.println(numEntries + " words entered.");

                in.close();
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error reading dictionary file at: " + wordlistFilePath);
                System.out.println("Line count: " + lineCount);
            }

            lineCount = 0;

    }

    private int readFile(BufferedReader in, String usage) throws IOException {
        List<WordEntry> wordEntries = new ArrayList<>();

        while(endOfFileFlag == false) {
            String nextWord = readLineToWord(in).trim();
            WordEntry newWordEntry = new WordEntry(nextWord, usage);
            wordEntries.add(newWordEntry);
        }

        endOfFileFlag = false;
        lineCount = 0;

        return wordEntryDao.batchCreateWordEntries(wordEntries);

    }

    private String readLineToWord(BufferedReader in) throws IOException {

        String word = "";
        int i = -1;

        while((i = in.read()) != -1 &&
                i != (int)'\r' &&
                i != (int) '\n'){
            word += (char)i;
        }

        if(i == -1){
            endOfFileFlag = true;
        }

        lineCount += 1;
        return word;
    }

}
