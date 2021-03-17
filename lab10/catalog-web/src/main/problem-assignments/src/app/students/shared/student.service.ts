import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {SortColumnDto, SortDto, Student, Students} from "./student.model";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Injectable()
export class StudentService {
  private studentsUrl = 'http://localhost:8079/api/students';

  constructor(private httpClient: HttpClient) {
  }

//  httpOptions = {
//   headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'}),
//   withCredentials: true,observe:'response'
// };

   httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'my-auth-token'
    })
  };
  // private getRequestOptions() {
  //   const headers = new Headers({
  //     'Content-Type': 'application/json',
  //   });
  //   return new RequestOptions({headers: headers, withCredentials: true});
  // }

  getStudents(): Observable<Students> {
    console.log('getStudents() enter');
    const result = this.httpClient.get<Students>(this.studentsUrl,{withCredentials:true});
    console.log('getStudents() exit ', result);
    return result;
  }

  getStudent(id: Number): Observable<Student> {
    console.log('getStudent() enter ', id);
    const url = `${this.studentsUrl}/${id}`;
    console.log('getStudent() path', url);
    //@ts-ignore
    const result = this.httpClient.get<Student>(url, {withCredentials:true});
    console.log('getStudent() finished ', result);
    return result;
  }

  saveStudent(student: Student): Observable<Student> {
    console.log("saveStudent() entered", student);
    const result = this.httpClient.post<Student>(this.studentsUrl, student,{withCredentials:true});
    console.log("saveStudent() finished", result);
    return result;
  }

  updateStudent(student: Student): Observable<Student> {
    console.log('updateStudent() enter ', student);
    const result = this.httpClient.put<Student>(this.studentsUrl, student,{withCredentials:true});
    console.log('updateStudent() finished ', result);
    return result;
  }

  deleteStudent(id: Number): Observable<any> {
    console.log('deleteStudent() enter ', id);
    const url = `${this.studentsUrl}/${id}`
    console.log('deleteStudent() path', url);
    const result = this.httpClient.delete(url,{withCredentials:true});
    console.log('getStudent() finished ', result);
    return result;
  }

  getStudentsPaged(pageNumber: number, pageSize: number): Observable<any> {
    console.log('getStudentsPaged() enter', pageNumber, pageSize);
    const result = this.httpClient.get(`${this.studentsUrl}/paged?pageNumber=${pageNumber}&pageSize=${pageSize}`,{withCredentials:true});
    console.log('getStudentsPaged() exit ', result);
    return result;

  }

  getStudentsPagedFilteredByGroupNumber(filterNumber: number) {
    console.log('getStudentsPagedFiltered() enter', filterNumber);
    const url = `${this.studentsUrl}/filter/groupNumber/${filterNumber}`;
    console.log('getStudentsPagedFiltered() path', url);
    // @ts-ignore
    const result = this.httpClient.get<Students>(url,{withCredentials:true});
    console.log('getStudentsPagedFiltered() exit ', result);
    return result;

  }

  getStudentsPagedFilteredByName(filtername: string) {
    console.log('getStudentsPagedFiltered() enter', filtername);
    const url = `${this.studentsUrl}/filter/name/${filtername}`;
    console.log('getStudentsPagedFiltered() path', url);
    // @ts-ignore
    const result = this.httpClient.get<Students>(url,{withCredentials:true});
    console.log('getStudentsPagedFiltered() exit ', result);
    return result;

  }


  getStudentsSorted(sort: SortDto): Observable<Students> {
    console.log('getStudentsSorted() enter', sort);
    const url = `${this.studentsUrl}/sorted`;
    console.log('getStudentsSorted() path', url);
    const result = this.httpClient.post<Students>(url, sort,{withCredentials:true});
    console.log('getStudentsSorted() exit ', result);
    return result;

  }
}
