package com.otto.crudspring.dto.mapper;

import com.otto.crudspring.dto.CourseDTO;
import com.otto.crudspring.dto.LessonDTO;
import com.otto.crudspring.enums.CategoryEnum;
import com.otto.crudspring.model.Course;
import com.otto.crudspring.model.Lesson;
import lombok.AllArgsConstructor;
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

        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
            Lesson lesson = new Lesson();
            lesson.setId(lessonDTO.id());
            lesson.setName(lessonDTO.name());
            lesson.setUrl(lessonDTO.url());
            lesson.setCourse(course);
            return lesson;
        }).collect(Collectors.toList());

        course.setLessons(lessons);
        return course;

    }

    public static CategoryEnum convertCategoryToValue(String value) {
        if (value == null) {
            return null;
        }

        return switch (value) {
            case "Front-end" -> CategoryEnum.FRONT_END;
            case "Back-end" -> CategoryEnum.BACK_END;
            default -> throw new IllegalArgumentException("Invalid category value: " + value);
        };
    }
}
