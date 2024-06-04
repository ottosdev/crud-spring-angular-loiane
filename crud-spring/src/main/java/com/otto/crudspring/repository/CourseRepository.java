package com.otto.crudspring.repository;

import com.otto.crudspring.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.status = 'Ativo'")
    List<Course> findByStatusAtivo();
}
