import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {LabProblem, LabProblems} from "./labproblems.model";
import {SortDto, Students} from "../../students/shared/student.model";

@Injectable({
  providedIn: 'root'
})
export class LabproblemsService {
  private labProblemsUrl = 'http://localhost:8079/api/lab_problems';

  constructor(private httpClient: HttpClient) {
  }


  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'}),
    withCredentials: true
  };

  getLabProblems(): Observable<LabProblems> {
    console.log('getLabProblems() enter');
    const result = this.httpClient.get<LabProblems>(this.labProblemsUrl,this.httpOptions);
    console.log('getLabProblems() finished', result);
    return result;

  }

  getLabProblemsPaged(pageNumber, pageSize): Observable<any> {
    console.log('getLabProblems() enter');
    const result = this.httpClient.get(`${this.labProblemsUrl}/paged?pageNumber=${pageNumber}&pageSize=${pageSize}`,this.httpOptions);
    console.log('getLabProblems() finished', result);
    return result;

  }

  getLabProblem(id: Number): Observable<LabProblem> {
    console.log('getLabProblem() enter ', id);
    const url = `${this.labProblemsUrl}/${id}`;
    console.log('getLabProblem() path', url);
    const result = this.httpClient.get<LabProblem>(url,this.httpOptions);
    console.log('getLabProblem() finished ', result);
    return result;
  }

  saveLabProblem(labProblem: LabProblem): Observable<LabProblem> {
    console.log("saveLabProblem() entered", labProblem);
    const result = this.httpClient.post<LabProblem>(this.labProblemsUrl, labProblem,this.httpOptions);
    console.log("saveLabProblem() finished", result);
    return result;
  }

  updateLabProblem(labProblem: LabProblem): Observable<LabProblem> {
    console.log('updateLabProblem() enter ', labProblem);
    const result = this.httpClient.put<LabProblem>(this.labProblemsUrl, labProblem,this.httpOptions);
    console.log('updateLabProblem() finished ', result);
    return result;
  }

  deleteLabProblem(id: Number): Observable<any> {
    console.log('deleteLabProblem() enter ', id);
    const url = `${this.labProblemsUrl}/${id}`
    console.log('deleteLabProblem() path', url);
    const result = this.httpClient.delete(url);
    console.log('deleteLabProblem() finished ', result);
    return result;
  }

  geLabProblemsSorted(sort: SortDto): Observable<LabProblems> {
    console.log('geLabProblemsSorted() enter', sort);
    const url = `${this.labProblemsUrl}/sorted`;
    console.log('geLabProblemsSorted() path', url);
    const result = this.httpClient.post<LabProblems>(url, sort,this.httpOptions);
    console.log('geLabProblemsSorted() exit ', result);
    return result;
  }

  getLabProblemsFilteredByProblemNumber(filterNumber: number): Observable<LabProblems> {
    console.log('getStudentsPagedFiltered() enter', filterNumber);
    const url = `${this.labProblemsUrl}/filter/problemNumber/${filterNumber}`;
    console.log('getStudentsPagedFiltered() path', url);
    const result = this.httpClient.get<LabProblems>(url,this.httpOptions);
    console.log('getStudentsPagedFiltered() exit ', result);
    return result;

  }

  getLabProblemsFilteredByDescription(filterValue: string): Observable<LabProblems> {
    console.log('getStudentsPagedFiltered() enter', filterValue);
    const url = `${this.labProblemsUrl}/filter/description/${filterValue}`;
    console.log('getStudentsPagedFiltered() path', url);
    const result = this.httpClient.get<LabProblems>(url,this.httpOptions);
    console.log('getStudentsPagedFiltered() exit ', result);
    return result;

  }

}
