import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from "@angular/common";
import {switchMap} from "rxjs/operators";
import {AssignmentService} from "../shared/assignment.service";
import {Assignment} from "../shared/assignment.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-assignment-change-detail',
  templateUrl: './assignment-change-detail.component.html',
  styleUrls: ['./assignment-change-detail.component.css']
})
export class AssignmentChangeDetailComponent implements OnInit {

  @Input() assignment: Assignment;

  statusString = 'Not submitted';
  submitted = false;
  assignmentFormGroup: FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private assignmentService: AssignmentService,
    private route: ActivatedRoute,
    private location: Location
  ) {
  }

  get getAssignmentForm() {
    return this.assignmentFormGroup.controls;
  }

  ngOnInit(): void {

    this.assignmentFormGroup = this.formBuilder.group({
      grade: ['', [Validators.required, Validators.min(1), Validators.max(10), Validators.pattern("^[A-Za-z0-9]+$")]],
    });


    this.route.params.pipe(switchMap((params: Params) => this.assignmentService.getAssignment(+params['id'])))
      .subscribe(student => this.assignment = student)
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submitted = true;
    if (this.assignmentFormGroup.invalid) {
      return;
    }
    if (!this.validateInput())
      return;
    console.log("updating assignment ", this.assignment);
    this.assignmentService.updateAssignment(this.assignment).subscribe(_ => {
      console.log("update finished");
      this.statusString = "Update has been finished!";
    });
  }

  validateInput(): boolean {
    console.log("validateInput() entered ", this.assignment);
    this.statusString = '';
    if (this.assignment.studentID < 0) {
      this.statusString += 'Invalid student ID, must be a positive value. ';
    }
    if (!Number.isInteger(this.assignment.studentID.valueOf())) {
      this.statusString += 'Invalid student ID, must be an integer.';
    }

    if (this.assignment.labProblemID < 0) {
      this.statusString += 'Invalid lab problem ID, must be a positive value. ';
    }
    if (!Number.isInteger(this.assignment.labProblemID.valueOf())) {
      this.statusString += 'Invalid lab problem ID, must be an integer.';
    }

    if (this.assignment.grade != null) {

      if (!Number.isInteger(this.assignment.grade.valueOf())) {
        this.statusString += 'Invalid grade, must be an integer.';
      }
      if (this.assignment.grade < 0 || this.assignment.grade > 10) {
        this.statusString += 'Invalid grade, must be between 0 and 10.';
      }
    } else {
      this.statusString += 'Invalid grade, must have a value.';

    }
    if (this.statusString.length != 0) {
      console.log("validateInput() fail");
      return false;
    }
    console.log("validateInput() success");
    return true;
  }
}
