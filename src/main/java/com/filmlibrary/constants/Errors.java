package com.filmlibrary.constants;

public interface Errors {
    class Books {
        public static final String FILM_DELETE_ERROR = "Фильм не может быть удален, так как у него есть активные аренды";
    }
    
    class Authors{
        public static final String DIRECTOR_DELETE_ERROR = "Режиссер не может быть удален, так как у его фильмов есть активные аренды";
    }
    
    class Users{
        public static final String USER_FORBIDDEN_ERROR = "У вас нет прав просматривать информацию о пользователе";
    }
}
