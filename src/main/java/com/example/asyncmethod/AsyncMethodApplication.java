package com.example.asyncmethod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * https://spring-projects.ru/guides/async-method/
 * https://spring.io/guides/gs/async-method/
  аннотация Spring @Async работает с веб-приложениями, но вам не нужно настраивать веб-контейнер, чтобы увидеть его преимущества.
 */
// * https://translate.googleusercontent.com/translate_c?depth=1&rurl=translate.google.com.ua&sl=en&sp=nmt4&tl=ru&u=https://spring.io/guides/gs/async-method/&xid=17259,15700022,15700186,15700191,15700256,15700259,15700262,15700265,15700271&usg=ALkJrhgLjnXfq87hGqdo2wWl3JThwEI1Sw

@SpringBootApplication
@EnableAsync
public class AsyncMethodApplication {

    public static void main(String[] args) {
        //закройте контекст приложения, чтобы закрыть пользовательский ExecutorService
        SpringApplication.run(AsyncMethodApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }
}
