package com.violetgoat.wordgameserver.controller;

import com.violetgoat.wordgameserver.dao.WordEntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@PreAuthorize("permitAll()")
public class WordListController {

    private final WordEntryDao wordEntryDao;
    private static final String USAGE = "common";

    @Autowired
    public WordListController(WordEntryDao wordEntryDao){
        this.wordEntryDao = wordEntryDao;
    }

    @CrossOrigin("http://localhost:63343")
    @RequestMapping(path = "/random/{length}", method = RequestMethod.GET)
    public String getRandomWordEntry(@PathVariable int length){
        Random random = new Random();
        List<String> wordList = wordEntryDao.getAllWordsWithLengthAndUsage(length, USAGE);
        int randIndex = random.nextInt(wordList.size());
        return wordList.get(randIndex);
    }

}
