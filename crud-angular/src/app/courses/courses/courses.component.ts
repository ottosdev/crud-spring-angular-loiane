import {Component, OnInit, ViewChild} from '@angular/core';
import {Course} from "./model/course";
import {CourseService} from "../services/course.service";
import {catchError, Observable, of, tap} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {ErrorDialogComponent} from "../../shared/components/error-dialog/error-dialog.component";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmationDialogComponent} from "../../shared/components/confirmation-dialog/confirmation-dialog.component";
import {CoursePage} from "./model/course-page";
import {MatPaginator, PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  courses$: Observable<CoursePage> | null = null;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageIndex: number = 0;
  pageSize: number = 10;

  constructor(
    private courseService: CourseService,
    private dialog: MatDialog,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar,
  ) {
    this.refresh()
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.courses$ = this.courseService.list(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }),
        catchError(error => {
          this.onError('Erro ao carregar cursos.');
          return of({ courses: [], totalElements: 0, totalPages: 0 })
        })
      );
  }

  onError(errorMessage: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMessage
    });
  }

  ngOnInit(): void {
  }

  onAdd() {
    this.router.navigate(['new'], {relativeTo: this.activatedRoute});
  }

  onEdit(course: Course): void {
    this.router.navigate(['edit', course.id], {relativeTo: this.activatedRoute});
  }

  onDelete(course: Course): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: "Deseja excluir: " + course.name + "?",
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.courseService.remove(course.id).subscribe({
          next: (res) => {
            this.handleSuccess()
            this.refresh()
          },
          error: err => {
            this.handleError('Error ao remover curso')
          }
        });
      }
    });

  }

  private handleSuccess() {
    this.snackBar.open("Curso removido com sucesso!", 'X', {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'center'
    });
  }

  private handleError(error: string) {
    this.snackBar.open(error, 'X', {
      duration: 3000,
    });
  }


}
