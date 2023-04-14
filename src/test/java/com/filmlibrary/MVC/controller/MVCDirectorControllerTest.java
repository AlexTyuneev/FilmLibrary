package com.filmlibrary.MVC.controller;

import com.filmlibrary.dto.DirectorDTO;
import com.filmlibrary.service.DirectorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
public class MVCDirectorControllerTest
      extends CommonTestMVC {
    
    //Создаем нового автора для создания через контроллер (тест дата)
    private final DirectorDTO directorDTO = new DirectorDTO("MVC_TestDirectorFio", "2023-01-01", "Test Description", new HashSet<>(), false);
    private final DirectorDTO directorDTOUpdated = new DirectorDTO("MVC_TestDirectorFio_UPDATED", "2023-01-01", "Test Description", new HashSet<>(), false);
    
    @Autowired
    private DirectorService directorService;
    
    /**
     * Метод, тестирующий просмотр всех авторов через MVC-контроллер.
     * Ожидаем, что результат ответа будет просто любой 200 статус.
     * Проверяем, что view, на которое нас перенаправит контроллер, при удачном вызове - это как раз показ всех авторов
     * Так-же, ожидаем, что в модели будет атрибут directors.
     *
     * @throws Exception - любая ошибка
     */
    @Test
    @DisplayName("Просмотр всех режиссеров через MVC контроллер, тестирование 'director/'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void listAll() throws Exception {
        log.info("Тест по выбору всех режиссеров через MVC начат");
        MvcResult result = mvc.perform(get("/directors")
                                             .param("page", "1")
                                             .param("size", "5")
                                             .contentType(MediaType.APPLICATION_JSON_VALUE)
                                             .accept(MediaType.APPLICATION_JSON_VALUE)
                                      )
              .andDo(print())
              .andExpect(status().is2xxSuccessful())
              .andExpect(view().name("directors/viewAllDirectors"))
              .andExpect(model().attributeExists("directors"))
              .andReturn();
    }
    
    /**
     * Метод, тестирующий создание автора через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном создании
     *
     * @throws Exception - любая ошибка
     */
    @Test
    @DisplayName("Создание режиссера через MVC контроллер, тестирование 'directors/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void createObject() throws Exception {
        log.info("Тест по созданию режиссера через MVC начат успешно");
        mvc.perform(post("/directors/add")
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .flashAttr("directorForm", directorDTO)
                          .accept(MediaType.APPLICATION_JSON_VALUE)
                          .with(csrf()))
              .andDo(print())
              .andExpect(status().is3xxRedirection())
              .andExpect(view().name("redirect:/directors"))
              .andExpect(redirectedUrlTemplate("/directors"))
              .andExpect(redirectedUrl("/directors"));
        log.info("Тест по созданию режиссера через MVC закончен успешно");
    }
    
    @Order(2)
    @Test
    @DisplayName("Обновление режиссера через MVC контроллер, тестирование 'directors/update'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    //@WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "andy_user")
    @Override
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению автора через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "directorFio"));
        DirectorDTO foundDirectorForUpdate = directorService.searchDirectors(directorDTO.getDirectorFio(), pageRequest).getContent().get(0);
        foundDirectorForUpdate.setDirectorFio(directorDTOUpdated.getDirectorFio());
        mvc.perform(post("/directors/update")
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .flashAttr("directorForm", foundDirectorForUpdate)
                          .accept(MediaType.APPLICATION_JSON_VALUE)
                   )
              .andDo(print())
              .andExpect(status().is3xxRedirection())
              .andExpect(view().name("redirect:/directors"))
              .andExpect(redirectedUrl("/directors"));
        log.info("Тест по обновлению режиссера через MVC закончен успешно");
    }
    
    @Order(3)
    @Test
    @DisplayName("Софт удаление автора через MVC контроллер, тестирование 'directors/delete'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void deleteObject() throws Exception {
        log.info("Тест по soft удалению автора через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "directorFio"));
        DirectorDTO foundDirectorForDelete = directorService.searchDirectors(directorDTOUpdated.getDirectorFio(), pageRequest).getContent().get(0);
        foundDirectorForDelete.setDeleted(true);
        mvc.perform(get("/directors/delete/{id}", foundDirectorForDelete.getId())
                          .contentType(MediaType.APPLICATION_JSON_VALUE)
                          .accept(MediaType.APPLICATION_JSON_VALUE)
                   )
              .andDo(print())
              .andExpect(status().is3xxRedirection())
              .andExpect(view().name("redirect:/directors"))
              .andExpect(redirectedUrl("/directors"));
        DirectorDTO deletedDirector = directorService.getOne(foundDirectorForDelete.getId());
        assertTrue(deletedDirector.isDeleted());
        log.info("Тест по soft удалению автора через MVC закончен успешно");
        directorService.deleteHard(foundDirectorForDelete.getId());
    }
}
