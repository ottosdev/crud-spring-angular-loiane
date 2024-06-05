package com.otto.crudspring.services;

import com.otto.crudspring.dto.CourseDTO;
import com.otto.crudspring.dto.mapper.CourseMapper;
import com.otto.crudspring.exception.RecordNotFoundException;
import com.otto.crudspring.model.Course;
import com.otto.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Validated
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
        this.courseRepository = courseRepository;
    }

    public List<CourseDTO> list() {
        return courseRepository.findByStatusAtivo().stream()
                .map(CourseMapper::toDTO).collect(Collectors.toList());
    }

    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id).map(CourseMapper::toDTO).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO courseDTO) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseDTO)));

    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        return courseOptional.map(c -> {
            Course course = courseMapper.toEntity(courseDTO);
            c.setName(courseDTO.name());
            c.setCategory(courseMapper.convertCategoryToValue(courseDTO.category()));
            c.getLessons().clear();
            course.getLessons().forEach(lesson -> c.getLessons().add(lesson));
            return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseDTO)));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@PathVariable @NotNull Long id) {
        courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
