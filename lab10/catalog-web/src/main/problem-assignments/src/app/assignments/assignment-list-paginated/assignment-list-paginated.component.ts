import {Component, OnInit} from '@angular/core';
import {Assignment} from "../shared/assignment.model";
import {Router} from "@angular/router";
import {AssignmentService} from "../shared/assignment.service";

@Component({
  selector: 'app-assignment-list-paginated',
  templateUrl: './assignment-list-paginated.component.html',
  styleUrls: ['./assignment-list-paginated.component.css']
})
export class AssignmentListPaginatedComponent implements OnInit {

  errorMessage: String
  assignments: Assignment[]
  pages: Array<number>;
  selectedAssignment: Assignment;
  currentPage: number;
  readonly pageSize = 5;
  searchTerm: string;

  constructor(
    private router: Router,
    private assignmentService: AssignmentService
  ) {
  }

  ngOnInit(): void {
    this.currentPage = 0;
    this.getAllAssignments()
  }

  public getAllAssignments() {
    this.assignmentService.getAllAssignmentsPaged(this.currentPage, this.pageSize).subscribe(
      assignments => {
        this.assignments = assignments['content'];
        this.pages = new Array(assignments['totalPages']);
        if (this.assignments.length == 0) {
          if (this.currentPage > 0) {
            this.currentPage -= 1;
            this.getAllAssignments();
          }
        }
        console.log("list adjusted");

      },
      error => {
        this.errorMessage = <any>error;
        console.log(error)
      }
    )
  }

  onSelect(assignment: Assignment): void {
    this.selectedAssignment = assignment;
  }

  gotoDetail(): void {
    this.router.navigate(['/assignments/updateDetail', this.selectedAssignment.id])
  }

  deleteAssignment(assignment: Assignment): void {
    console.log("deleteAssignment() entered: ", assignment)
    this.assignmentService.deleteAssignment(assignment.id).subscribe(_ => {
      console.log("deleteAssignment() exit");
      this.assignments = this.assignments.filter(s => s.id != assignment.id);
      this.getAllAssignments();

    });
  }

  setPage(i: number, event: any) {
    event.preventDefault();
    this.currentPage = i;
    this.getAllAssignments();
  }

  sortBy(column: string) {
    this.assignments = this.assignments.sort((a, b) => a[column].localeCompare(b[column]));
  }

  sortByNumeric(column: string) {
    this.assignments = this.assignments.sort((a, b) => a[column] - (b[column]));
  }
}
