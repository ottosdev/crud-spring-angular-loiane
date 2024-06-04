package com.otto.crudspring.model;

import com.otto.crudspring.enums.CategoryEnum;
import com.otto.crudspring.enums.Status;
import com.otto.crudspring.enums.converters.CategoryEnumConverters;
import com.otto.crudspring.enums.converters.StatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_courses")
@SQLDelete(sql = "UPDATE tb_courses SET status = 'Inativo' where id = ?")
//@Where(clause = "status = 'Ativo'")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull
    @Column(nullable = false, length = 10)
    @Convert(converter = CategoryEnumConverters.class)
    private CategoryEnum category;

    @NotNull
    @Convert(converter = StatusConverter.class)
    @Column(nullable = false, length = 10)
    private Status status = Status.ACTIVE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();
}
