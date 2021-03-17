import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SortDto} from "../../students/shared/student.model";
import {Assignment} from "../shared/assignment.model";
import {AssignmentService} from "../shared/assignment.service";

@Component({
  selector: 'app-assignment-sort',
  templateUrl: './assignment-sort.component.html',
  styleUrls: ['./assignment-sort.component.css']
})
export class AssignmentSortComponent implements OnInit {

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


  deleteLabProblem(assignment: Assignment): void {
    console.log("deleting assignment: ", assignment)
    this.assignmentService.deleteAssignment(assignment.id).subscribe(_ => {
      console.log("assignment deleted");
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
        this.errorMessage = "Invalid sort criterias";
        console.log('HTTP error', error)
      }
    );

  }
}
