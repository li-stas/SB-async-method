package com.example.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/**
 * Класс GitHubLookupService использует Spring RestTemplate для вызова удаленной точки REST (api.github.com/users/)
 * и затем преобразует ответ в объект User . Spring Boot автоматически предоставляет RestTemplateBuilder
 * который настраивает значения по умолчанию с любыми битами автоконфигурации (то есть MessageConverter ).
 *
 * Класс помечен аннотацией @Service , что делает его кандидатом на сканирование компонентов
 * Spring для обнаружения и добавления в контекст приложения.
 *
 * Метод findUser помечается аннотацией Spring @Async , указывающей, что он должен выполняться в отдельном потоке.
 * Тип возврата метода - CompletableFuture<User> вместо User , требование для любой асинхронной службы.
 * Этот код использует метод completedFuture для возврата экземпляра CompletableFuture который уже
 * завершен с результатом запроса GitHub.
 *
 * Создание локального экземпляра класса GitHubLookupService НЕ позволяет методу findUser работать асинхронно.
 * Он должен быть создан внутри класса @Configuration или подобран @ComponentScan .
 * Время для API GitHub может варьироваться. Чтобы продемонстрировать преимущества, приведенные далее в этом
 * руководстве, к этой услуге была добавлена ​​дополнительная задержка в одну секунду.
 */

@Service
public class GitHubLookupService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

}
