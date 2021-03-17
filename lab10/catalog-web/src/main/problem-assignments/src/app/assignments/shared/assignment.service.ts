import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Assignment, Assignments} from "./assignment.model";
import {HttpClient} from "@angular/common/http";
import {LabProblems} from "../../labproblems/shared/labproblems.model";
import {SortDto} from "../../students/shared/student.model";

@Injectable({
  providedIn: 'root'
})
export class AssignmentService {
  private assignmentsUrl = 'http://localhost:8079/api/assignments';

  constructor(
    private httpClient: HttpClient
  ) {
  }

  getAllAssignments(): Observable<Assignments> {
    console.log('getAllAssignments() enter');
    const result = this.httpClient.get<Assignments>(this.assignmentsUrl,{withCredentials:true});
    console.log('getAllAssignments() finished', result);
    return result;
  }

  getAllAssignmentsPaged(pageNumber, pageSize): Observable<any> {
    console.log('getAllAssignments() enter');
    const result = this.httpClient.get(`${this.assignmentsUrl}/paged?pageNumber=${pageNumber}&pageSize=${pageSize}`,{withCredentials:true});
    console.log('getAllAssignments() finished', result);
    return result;
  }

  getAssignment(id: Number): Observable<Assignment> {
    console.log('getAssignment() enter ', id);
    const url = `${this.assignmentsUrl}/${id}`;
    console.log('getAssignment() path', url);
    const result = this.httpClient.get<Assignment>(url,{withCredentials:true});
    console.log('getAssignment() finished ', result);
    return result;
  }

  saveAssignment(assignment: Assignment): Observable<Assignment> {
    console.log("saveAssignment() entered", assignment);
    const result = this.httpClient.post<Assignment>(this.assignmentsUrl, assignment,{withCredentials:true});
    console.log("saveAssignment() finished", result);
    return result;
  }

  updateAssignment(assignment: Assignment): Observable<Assignment> {
    console.log('updateAssignment() enter ', assignment);
    console.log('updateAssignment() path', this.assignmentsUrl);
    const result = this.httpClient.put<Assignment>(this.assignmentsUrl, assignment,{withCredentials:true});
    console.log('updateAssignment() finished ', result);
    return result;
  }

  deleteAssignment(id: Number): Observable<any> {
    console.log('deleteAssignment() enter ', id);
    const url = `${this.assignmentsUrl}/${id}`
    console.log('deleteAssignment() path', url);
    const result = this.httpClient.delete(url,{withCredentials:true});
    console.log('deleteAssignment() finished ', result);
    return result;
  }


  getAssignmentsForStudent(id: Number): Observable<Assignments> {
    console.log('getAssignmentsForStudent() enter');
    const url = `${this.assignmentsUrl}/student/${id}`;
    console.log('getAssignmentsForStudent() path', url);
    const result = this.httpClient.get<Assignments>(url,{withCredentials:true});
    console.log('getAssignmentsForStudent() finished', result);
    return result;
  }

  getAvailableLabProblemsForStudent(id: Number): Observable<LabProblems> {
    console.log('getAvailableLabProblemsForStudent() enter');
    const url = `${this.assignmentsUrl}/student/available/${id}`;
    console.log('getAvailableLabProblemsForStudent() path', url);
    const result = this.httpClient.get<LabProblems>(url,{withCredentials:true});
    console.log('getAvailableLabProblemsForStudent() finished', result);
    return result;
  }

  getMostAssignedLabProblem(): Observable<any> {
    console.log('getMostAssignedLabProblem() enter ');
    const url = `${this.assignmentsUrl}/mostAssigned`;
    console.log('getMostAssignedLabProblem() path', url);
    const result = this.httpClient.get(url,{withCredentials:true});
    console.log('getMostAssignedLabProblem() finished ', result);
    return result;

  }

  getGreatestMeanStudent(): Observable<any> {
    console.log('getGreatestMeanStudent() enter ');
    const url = `${this.assignmentsUrl}/greatestMean`;
    console.log('getGreatestMeanStudent() path', url);
    const result = this.httpClient.get(url,{withCredentials:true});
    console.log('getGreatestMeanStudent() finished ', result,{withCredentials:true});
    return result;

  }

  getAverageGrade(): Observable<any> {
    console.log('getAverageGrade() enter ');
    const url = `${this.assignmentsUrl}/averageGrade`;
    console.log('getAverageGrade() path', url);
    const result = this.httpClient.get(url,{withCredentials:true});
    console.log('getAverageGrade() finished ', result);
    return result;
  }

  getAssignmentsSorted(sort: SortDto): Observable<Assignments> {
    console.log('getAssignmentsSorted() enter', sort);
    const url = `${this.assignmentsUrl}/sorted`;
    console.log('getAssignmentsSorted() path', url);
    const result = this.httpClient.post<Assignments>(url, sort,{withCredentials:true});
    console.log('getAssignmentsSorted() exit ', result);
    return result;
  }

  getAssignmentsFiltered(filterNumber: number): Observable<Assignments> {
    console.log('getAssignmentsFiltered() enter', filterNumber);
    const url = `${this.assignmentsUrl}/filter/${filterNumber}`;
    console.log('getAssignmentsFiltered() path', url);
    const result = this.httpClient.get<Assignments>(url,{withCredentials:true});
    console.log('getAssignmentsFiltered() exit ', result);
    return result;

  }

}
