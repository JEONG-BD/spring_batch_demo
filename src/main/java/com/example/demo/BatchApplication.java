package com.example.demo;


import com.example.demo.entity.AfterEntity;
import com.example.demo.entity.BeforeEntity;
import com.example.demo.repository.AfterRepository;
import com.example.demo.repository.BeforeRepository;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class);
    }

    @Bean
    public CommandLineRunner loadData(BeforeRepository beforeRepository){
        return  args -> {
            for (int i = 1; i < 1001; i++) {
                BeforeEntity beforeEntity = new BeforeEntity();
                beforeEntity.setUsername("test" + i);
                beforeRepository.save(beforeEntity);
            }
        };
    }
}
