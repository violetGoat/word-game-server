package com.violetgoat.wordgameserver.model;

import javax.validation.constraints.NotBlank;
import java.util.Locale;

public class WordEntry {

    int id;

    @NotBlank
    private String word;
    @NotBlank
    private String usage;

    private static String[] validUsages = {"common", "not-common"};

    public WordEntry() {
    }

    public WordEntry(int id, String word, String usage) {
        if(usage != "common" && usage != "not-common" ){
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.word = word;
        this.usage = usage;
    }

    public WordEntry(String word, String usage){
        this(0, word, usage);
    }

    public int getId() {
        return id;
    }

    public WordEntry setId(int id) {
        this.id = id;
        return this;
    }

    public String getWord() {
        return word.toLowerCase();
    }

    public WordEntry setWord(String word) {
        if(!word.matches("[a-zA-Z]*")){
            System.out.println(word);
            throw new IllegalArgumentException("Word must contain only latin-alphabet characters");
        }
        this.word = word.toLowerCase();
        return this;
    }

    public String getUsage() {
        return usage.toLowerCase();
    }

    public WordEntry setUsage(String usage) {

        if(usage != "common" && usage != "not-common" ){
            throw new IllegalArgumentException();
        }

        this.usage = usage.toLowerCase();
        return this;
    }
}
