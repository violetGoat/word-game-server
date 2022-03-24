package com.violetgoat.wordgameserver.exception;

import com.violetgoat.wordgameserver.model.WordEntry;

public class WordEntryNotFoundException extends Exception {

    public WordEntryNotFoundException(){this("Word Entry Not Found.");}
    public WordEntryNotFoundException(String message){super(message);}

}
