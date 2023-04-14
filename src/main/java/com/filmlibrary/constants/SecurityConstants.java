package com.filmlibrary.constants;

import java.util.List;

public interface SecurityConstants {
    
    class REST {
        public static List<String> FILMS_WHITE_LIST = List.of("/rest/films",
                                                              "/rest/films/search",
                                                              "/rest/films/{id}");
        
        public static List<String> DIRECTORS_WHITE_LIST = List.of("/rest/directors",
                                                                "/rest/directors/search",
                                                                "/rest/films/search/author",
                                                                "/rest/directors/{id}");
        
        public static List<String> USERS_WHITE_LIST = List.of("/rest/users/auth",
                                                              "/rest/users/registration",
                                                              "/rest/users/remember-password",
                                                              "/rest/users/change-password");
        
        public static List<String> DIRECTORS_PERMISSION_LIST = List.of("/rest/directors/add",
                                                                     "/rest/directors/update",
//                                                                     "/rest/directors/delete/**",
                                                                     "/rest/directors/delete/{id}"
                                                                     );
        
        public static List<String> FILMS_PERMISSION_LIST = List.of("/rest/films/add",
                                                                   "/rest/films/update",
                                                                   "/rest/films/delete/**",
                                                                   "/rest/films/delete/{id}",
                                                                   "/rest/films/download/{bookId}");
        
        public static List<String> USERS_PERMISSION_LIST = List.of("/rest/rent/film/*");
    }
    
    List<String> RESOURCES_WHITE_LIST = List.of("/resources/**",
                                                "/js/**",
                                                "/css/**",
                                                "/",
                                                // -- Swagger UI v3 (OpenAPI)
                                                "/swagger-ui/**",
                                                "/webjars/bootstrap/5.0.2/**",
                                                "/v3/api-docs/**",
                                                "/error");
    
    List<String> FILMS_WHITE_LIST = List.of("/films",
                                            "/films/search",
                                            "/films/{id}");
    
    List<String> DIRECTORS_WHITE_LIST = List.of("/directors",
                                              "/directors/search",
                                              "/films/search/director",
                                              "/directors/{id}");
    List<String> FILMS_PERMISSION_LIST = List.of("/films/add",
                                                 "/films/update",
                                                 "/films/delete",
                                                 "/films/download/{bookId}");
    
    List<String> DIRECTORS_PERMISSION_LIST = List.of("/directors/add",
                                                   "/directors/update",
                                                   "/directors/delete");
    
    List<String> USERS_WHITE_LIST = List.of("/login",
                                            "/users/registration",
                                            "/users/remember-password",
                                            "/users/change-password");
    
    List<String> USERS_PERMISSION_LIST = List.of("/rent/film/*");
}
