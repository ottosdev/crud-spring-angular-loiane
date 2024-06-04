package com.otto.crudspring.database;

import com.otto.crudspring.enums.CategoryEnum;
import com.otto.crudspring.model.Course;
import com.otto.crudspring.model.Lesson;
import com.otto.crudspring.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Seeder {

    @Bean
    public CommandLineRunner seedData(CourseRepository crudRepository) {
        return args ->
            seedCursos(crudRepository);
    }

    public void seedCursos(CourseRepository crudRepository) {
        if (crudRepository.count() == 0) {
            Course curso1 = new Course();
            curso1.setName("Desenvolvimento Web");
            curso1.setCategory(CategoryEnum.FRONTEND);
            Lesson l = new Lesson();
            l.setName("Introducao ao react 19");
            l.setUrl("youtube");
            l.setCourse(curso1);
            curso1.getLessons().add(l);
            crudRepository.save(curso1);
        }
    }
}
