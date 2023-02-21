package com.filmlibrary.controller;

import com.filmlibrary.model.GenericModel;
import com.filmlibrary.repository.GenericRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Абстрактный контроллер
 * который реализует все EndPoint`ы для crud операций используя абстрактный репозиторий
 *
 * @param <T> - Сущность с которой работает контроллер
 */
@RestController
public abstract class GenericController<T extends GenericModel> {
    
    private final GenericRepository<T> genericRepository;
    
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GenericController(GenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }
    
    //вернуть информацию о книге по переданному ID
    @Operation(description = "Получить запись по ID", method = "getOneById")
    @RequestMapping(value = "/getOneById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> getOneById(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
              .body(genericRepository.findById(id).orElseThrow(() -> new NotFoundException("Данных с переданным ID не найдено")));
    }
    
    @Operation(description = "Получить все записи", method = "getAll")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(genericRepository.findAll());
    }
    
    @Operation(description = "Создать новую запись", method = "create")
    @RequestMapping(value = "/add", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<T> create(@RequestBody T newEntity) {
        genericRepository.save(newEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEntity);
    }
    
    @Operation(description = "Обновить запись", method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> update(@RequestBody T updatedEntity,
                                    @RequestParam(value = "id") Long id) {
        updatedEntity.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(genericRepository.save(updatedEntity));
    }
    
    //@RequestParam: localhost:9090/api/rest/books/deleteBook?id=1
    //@PathVariable: localhost:9090/api/rest/books/deleteBook/1
    @Operation(description = "Удалить запись по ID", method = "delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        genericRepository.deleteById(id);
    }
    
    /*
    TODO:
     1) https://habr.com/ru/post/483202/ - что такое REST и RESTFul + HTTP
     2) https://habr.com/ru/post/38730/ - Архитектура REST
     3) https://habr.com/ru/post/251669/ - REST с точки зрения производительности
     4) https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion - Bi-Directional ссылки при формировании JSON, решение
     цикличности
     5) https://www.javacodegeeks.com/2019/01/spring-custom-serializers-jsonidentityinfo.html
        https://coderlessons.com/articles/java/polzovatelskie-serializatory-spring-s-jsonidentityinfo - еще про проблемы цикличности
     6) https://www.baeldung.com/jackson-advanced-annotations - еще про аннотации JSON
        https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations
     7)https://github.com/mikebski/jackson-circular-reference - как можно заменить генерацию в JSON на UUID
     8) https://javarush.com/groups/posts/1034-zachem-ispoljhzovatjh-serialversionuid-vnutri-serializable-klassa-v-java - зачем использовать serialVersion
        https://itsobes.ru/JavaSobes/zachem-ispolzuetsia-serial-version-uid-chto-esli-ne-opredelit-ego/
        https://www.baeldung.com/java-14-serial-annotation
     */
}
