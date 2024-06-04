package com.otto.crudspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CourseDTO(
        Long id,

        @NotBlank
        @NotNull
        @Length(min = 5, max = 100)
        String name,

        @NotNull
        @Length(max = 10)
        @Pattern(regexp = "Back-end|Front-end")
        String category,

        List<LessonDTO> lessons
) {

}
