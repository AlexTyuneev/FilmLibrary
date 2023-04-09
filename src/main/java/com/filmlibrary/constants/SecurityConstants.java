package com.filmlibrary.constants;

import java.util.List;

public interface SecurityConstants {
    
    List<String> RESOURCES_WHITE_LIST = List.of("/resources/**",
                                                "/js/**",
                                                "/css/**",
                                                "/",
                                                // -- Swagger UI v3 (OpenAPI)
                                                "/swagger-ui/**",
                                                "/webjars/bootstrap/5.0.2/**",
                                                "/v3/api-docs/**");
    
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
                                                 "/films/download/{filmId}");
    
    List<String> DIRECTORS_PERMISSION_LIST = List.of("/directors/add",
                                                   "/directors/update",
                                                   "/directors/delete");
    
    List<String> USERS_WHITE_LIST = List.of("/login",
                                            "/users/registration",
                                            "/users/remember-password",
                                            "/users/change-password");
    
    List<String> USERS_PERMISSION_LIST = List.of("/rent/film/*");
    
    List<String> USERS_REST_WHITE_LIST = List.of("/users/auth");
}
