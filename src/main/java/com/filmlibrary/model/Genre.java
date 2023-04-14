package com.filmlibrary.model;

public enum Genre {
    FANTASY("Фэнтези"),
    SCI_FI("Скай-фай"),
    HORROR ("Ужасы"),
    DRAMA("Драма"),
    DETECTIVE("Детектив"),
    COMEDY("Комедия");
    
    private final String genreTextDisplay;
    
    Genre(String text) {
        this.genreTextDisplay = text;
    }
    
    public String getGenreTextDisplay() {
        return this.genreTextDisplay;
    }
}
