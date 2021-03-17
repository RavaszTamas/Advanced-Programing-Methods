import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Assignment} from "../shared/assignment.model";
import {AssignmentService} from "../shared/assignment.service";

@Component({
  selector: 'app-assignments-list',
  templateUrl: './assignments-list.component.html',
  styleUrls: ['./assignments-list.component.css']
})
export class AssignmentsListComponent implements OnInit {

  errorMessage: String
  assignments: Assignment[]
  selectedAssignment: Assignment;

  constructor(
    private router: Router,
    private assignmentService: AssignmentService
  ) {
  }

  ngOnInit(): void {
    this.getAllAssignments()
  }

  private getAllAssignments() {
    this.assignmentService.getAllAssignments().subscribe(
      assignments => this.assignments = assignments.assignments,
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
    });
  }

}
