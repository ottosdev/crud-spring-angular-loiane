package com.otto.crudspring.dto.mapper;

import com.otto.crudspring.dto.CourseDTO;
import com.otto.crudspring.dto.LessonDTO;
import com.otto.crudspring.enums.CategoryEnum;
import com.otto.crudspring.model.Course;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {
    public static CourseDTO toDTO(Course course) {
        List<LessonDTO> lessonsDTO =
                course.getLessons().stream()
                        .map(lesson ->
                                new LessonDTO(
                                        lesson.getId(),
                                        lesson.getName(),
                                        lesson.getUrl())).
                        collect(Collectors.toList());

        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCategory().getValue(),
                lessonsDTO);
    }

    public static Course toEntity(CourseDTO courseDTO) {
        Course course = new Course();
        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertCategoryToValue(courseDTO.category()));
        return course;

    }

    public static CategoryEnum convertCategoryToValue(String value) {
        if (value == null) {
            return null;
        }

        return switch (value) {
            case "Front-end" -> CategoryEnum.FRONTEND;
            case "Back-end" -> CategoryEnum.BACKEND;
            default -> throw new IllegalArgumentException("Invalid category value: " + value);
        };
    }
}
