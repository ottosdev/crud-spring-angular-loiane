package com.otto.crudspring.controller;

import com.otto.crudspring.dto.CourseDTO;
import com.otto.crudspring.dto.CoursePageDTO;
import com.otto.crudspring.services.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RequestMapping(value = "/api/courses")
@RestController

public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

//    @GetMapping
//    public List<CourseDTO> list() {
//        return courseService.list();
//    }

    @GetMapping
    public CoursePageDTO list(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                              @RequestParam(defaultValue = "10") @Positive @Max(100) int pageSize) {
        return courseService.list(page, pageSize);
    }

    @GetMapping("{id}")
    public CourseDTO findById(@PathVariable @NotNull Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO create(@RequestBody @Valid CourseDTO course) {
        return courseService.create(course);
    }

    @PutMapping("{id}")
    public CourseDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid CourseDTO course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull Long id) {
        courseService.delete(id);
    }
}
