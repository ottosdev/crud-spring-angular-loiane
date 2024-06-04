import {Component, OnInit} from '@angular/core';
import {FormGroup, NonNullableFormBuilder, UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";
import {CourseService} from "../services/course.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {Course} from "../courses/model/course";
import {first} from "rxjs";

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit{

  form!: FormGroup;

  constructor(private formBuilder: NonNullableFormBuilder,
              private courseService: CourseService,
              private snackBar: MatSnackBar,
              private location: Location,
              private route: ActivatedRoute
              ) {
  }

  handleSubmit() {
    const course = this.form.value;
    this.courseService.save(course).subscribe({
      next: response => {
        this.handleSuccess();
      },
      error: error => this.handleError("Error ao salvar curso.")
    });
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

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course']
    this.form = this.formBuilder.group({
      id: [course.id],
      name: [course.name, [Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100)]],
      category: [course.category, [Validators.required]],
    });
  }

  getErrorMessage(fieldName: string) {
    const fields = this.form.get(fieldName)
    if(fields?.hasError('required')) {
       return 'Campo Obrigatorio'
    }

    if(fields?.hasError('minlength')) {
      const count = fields?.errors? fields.errors['minlength']['requiredLength'] : 5;
      return `Tamanho minimo de ${count} caracteres`;
    }

    if(fields?.hasError('maxlength')) {
      const count = fields?.errors? fields.errors['maxlength']['requiredLength'] : 5;
      return `Tamanho maximo de ${count} caracteres`;
    }
    return 'Campo Invalido'
  }
}
