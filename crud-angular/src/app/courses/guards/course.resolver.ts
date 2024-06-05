import { ResolveFn } from '@angular/router';
import {CourseService} from "../services/course.service";
import {inject} from "@angular/core";
import {Course} from "../courses/model/course";
import {of} from "rxjs";


export const courseResolver: ResolveFn<Course> = (route, state) => {
  const courseService = inject(CourseService);
  if(route.params && route.params['id']) {
    return courseService.getById(route.params['id'])
  }
  return of({
    id: '',
    name: '',
    category: '',
    lessons: []
  });
};
