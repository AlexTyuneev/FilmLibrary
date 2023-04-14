package com.filmlibrary;

import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.model.Director;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface DirectorTestData {
    DirectorDTO DIRECTOR_DTO_1 = new DirectorDTO("directorFio1",
                                           "birthDate1",
                                           "description1",
                                           new HashSet<>(),
                                           false);

    DirectorDTO DIRECTOR_DTO_2 = new DirectorDTO("directorFio2",
                                           "birthDate2",
                                           "description2",
                                           new HashSet<>(),
                                           false);

    DirectorDTO DIRECTOR_DTO_3_DELETED = new DirectorDTO("directorFio3",
                                                   "birthDate3",
                                                   "description3",
                                                   new HashSet<>(),
                                                   true);
    
    List<DirectorDTO> DIRECTOR_DTO_LIST = Arrays.asList(DIRECTOR_DTO_1, DIRECTOR_DTO_2, DIRECTOR_DTO_3_DELETED);


    Director DIRECTOR_1 = new Director("director1",
                                 LocalDate.now(),
                                 "description1",
                                            null);

    Director DIRECTOR_2 = new Director("director2",
                                 LocalDate.now(),
                                 "description2", null);

    Director DIRECTOR_3 = new Director("director3",
                                 LocalDate.now(),
                                 "description3", null);
    
    List<Director> DIRECTOR_LIST = Arrays.asList(DIRECTOR_1, DIRECTOR_2, DIRECTOR_3);
}
