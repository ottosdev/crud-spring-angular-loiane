import {Component, OnInit} from '@angular/core';
import {
  FormGroup,
  NonNullableFormBuilder,
  UntypedFormArray,
  UntypedFormBuilder,
  UntypedFormGroup,
  Validators
} from "@angular/forms";
import {CourseService} from "../services/course.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {Course} from "../courses/model/course";
import {first} from "rxjs";
import {Lesson} from "../courses/model/lesson";
import {FormUtilsService} from "../../shared/form/form-utils.service";

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit {

  form!: FormGroup;

  constructor(private formBuilder: NonNullableFormBuilder,
              private courseService: CourseService,
              private snackBar: MatSnackBar,
              private location: Location,
              private route: ActivatedRoute,
              public formService: FormUtilsService
  ) {
  }

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course']
    this.form = this.formBuilder.group({
      id: [course.id],
      name: [course.name, [Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100)]],
      category: [course.category, [Validators.required]],
      lessons: this.formBuilder.array(this.retriveLessons(course), Validators.required)
    });
  }

  private retriveLessons(course: Course) {
    const lessons = []
    if (course?.lessons) {
      course.lessons.forEach(lesson => lessons.push(this.createLesson(lesson)))
    } else {
      lessons.push(this.createLesson())
    }
    return lessons;
  }

  private createLesson(lesson: Lesson = {id: '', name: '', url: ''}) {
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name, [
        Validators.minLength(5),
        Validators.maxLength(11)]],
      url: [lesson.url, [
        Validators.minLength(10),
        Validators.maxLength(11)]],
    })
  }

  getLessonsFormArray() {
    return (<UntypedFormArray>this.form.get('lessons')).controls;
  }

  addNewLesson() {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.push(this.createLesson());
  }

  removeLesson(index: number) {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.removeAt(index);
  }

  handleSubmit() {
    if (this.form.valid) {
      const course = this.form.value;
      this.courseService.save(course).subscribe({
        next: response => {
          this.handleSuccess();
        },
        error: error => this.handleError("Error ao salvar curso.")
      });
    } else {
      this.formService.validateAllFormFields(this.form);
    }
  }

  handleCancel() {
    this.location.back();
  }

  private handleSuccess() {
    this.snackBar.open("Curso salvo com sucesso", '', {
      duration: 3000,
    });
    this.handleCancel();
  }

  private handleError(error: string) {
    this.snackBar.open(error, '', {
      duration: 3000,
    });
  }
}
