package com.otto.crudspring.database;

import com.otto.crudspring.enums.CategoryEnum;
import com.otto.crudspring.model.Course;
import com.otto.crudspring.model.Lesson;
import com.otto.crudspring.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Seeder {

    @Bean
    @Profile("test")
    public CommandLineRunner seedData(CourseRepository crudRepository) {
        return args ->
            seedCursos(crudRepository);
    }

    @Transactional
    public void seedCursos(CourseRepository crudRepository) {
        for (int i = 0; i < 50; i++) {
                Course curso1 = new Course();
                curso1.setName("Desenvolvimento Web" + i);
                curso1.setCategory(CategoryEnum.FRONT_END);
                Lesson l = new Lesson();
                l.setName("Introducao ao react 19");
                l.setUrl("0123456789");
                l.setCourse(curso1);
                curso1.getLessons().add(l);
                crudRepository.save(curso1);

        }

    }
}
