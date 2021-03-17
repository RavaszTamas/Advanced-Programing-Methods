import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SortDto} from "../../students/shared/student.model";
import {AssignmentService} from "../shared/assignment.service";
import {Assignment} from "../shared/assignment.model";

@Component({
  selector: 'app-assignment-filter',
  templateUrl: './assignment-filter.component.html',
  styleUrls: ['./assignment-filter.component.css']
})
export class AssignmentFilterComponent implements OnInit {

  assignments: Assignment[]
  errorMessage: String
  selectedAssignment: Assignment;

  constructor(
    private router: Router,
    private assignmentService: AssignmentService
  ) {
  }

  ngOnInit(): void {
    this.getLabProblems()
  }

  private getLabProblems() {
  }

  onSelect(assignment: Assignment): void {
    this.selectedAssignment = assignment;
  }

  gotoDetail(): void {
    this.router.navigate(['/labproblems/detail', this.selectedAssignment.id])
  }


  deleteAssignment(assignment: Assignment): void {
    console.log("deleting labProblem: ", assignment)
    this.assignmentService.deleteAssignment(assignment.id).subscribe(_ => {
      console.log("labProblem deleted");
      this.assignments = this.assignments.filter(s => s.id != assignment.id);
    });
  }

  parseSortString(sortString: string): SortDto {
    var params = sortString.split(",");
    var result: SortDto = {sortColumnDtoList: []} as SortDto;
    try {
      for (let i = 0; i < params.length; i = i + 2) {
        result.sortColumnDtoList.push({direction: params[i], column: params[i + 1]})
      }
    } catch (error) {
      this.errorMessage = "Invalid string input";
      return null;
    }
    this.errorMessage = "";
    return result;
  }

  getLabProblemsSorted(sortString: string) {
    console.log(sortString)
    const sort = this.parseSortString(sortString);
    if (sort == null)
      return;

    console.log("getting students sorted", sort);
    this.assignmentService.getAssignmentsSorted(sort).subscribe(
      assignments => {
        console.log(assignments);
        this.assignments = assignments.assignments;
      },
      error => {
        this.errorMessage = <any>error;
        console.log('HTTP error', error)
      }
    );


  }

  getAssignmentsFiltered(filterValue: string) {
    let value = parseInt(filterValue);

    console.log("getting assignments filtered", value);
    this.assignmentService.getAssignmentsFiltered(value).subscribe(
      assignments => {
        console.log(assignments);
        this.assignments = assignments.assignments;
      },
      error => {
        this.errorMessage = <any>error;
        console.log('HTTP error', error)
      }
    );

  }

  String(grade: Number) {
    return grade.toString();
  }
}
