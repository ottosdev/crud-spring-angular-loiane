import {Injectable} from '@angular/core';
import {Course} from "../courses/model/course";
import {HttpClient} from "@angular/common/http";
import {first, Observable, take, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private readonly API_URL: string = 'api/courses';

  constructor(private http: HttpClient) {
  }

  list(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.API_URL}`, {})
      .pipe(
        first(), // Estou interessado apenas na primeira resposta que o servidor me enviar
        // tap(courses => console.log(courses))
      );
  }

  save(course: Partial<Course>) {
    if(course.id) {
     return this.update(course)
    }
   return this.create(course)
  }

  create(course: Partial<Course>){
    return this.http.post<Course>(`${this.API_URL}`, course).pipe(first());
  }

  update(course: Partial<Course>) {
    return this.http.put<Course>(`${this.API_URL}/${course.id}`, course).pipe(first());
  }

  getById(id: string){
    return this.http.get<Course>(`${this.API_URL}/${id}`);
  }

  remove(id: string) {
    return this.http.delete(`${this.API_URL}/${id}`);
  }
}
