import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AssignmentService} from "../shared/assignment.service";
import {Location} from "@angular/common";
import {Router} from "@angular/router";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StudentService} from "../../students/shared/student.service";
import {LabproblemsService} from "../../labproblems/shared/labproblems.service";

@Component({
  selector: 'app-assignment-new',
  templateUrl: './assignment-new.component.html',
  styleUrls: ['./assignment-new.component.css']
})
export class AssignmentNewComponent implements OnInit {

  statusString = 'Not submitted';
  submitted = false;
  assignmentFormGroup: FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private assignmentService: AssignmentService,
    private location: Location,
    private router: Router
  ) {
  }

  get getAssignmentForm() {
    return this.assignmentFormGroup.controls;
  }

  ngOnInit(): void {
    this.assignmentFormGroup = this.formBuilder.group({
      studentID: ['', [Validators.required, Validators.min(0), Validators.pattern("^[A-Za-z0-9]+$")]],
      labProblemID: ['', [Validators.required, Validators.min(0), Validators.pattern("^[A-Za-z0-9]+$")]],
      grade: ['', [Validators.required, Validators.min(1), Validators.max(10), Validators.pattern("^[A-Za-z0-9]+$")]],
    });

  }

  saveAssignment(studentID: string, labProblemID: string, grade: string) {
    console.log("saveAssignment() entered", studentID, labProblemID, grade);
    this.submitted = true;
    if (this.assignmentFormGroup.invalid) {
      return;
    }
    if (!this.validateInput(studentID, labProblemID, grade))
      return;
    this.assignmentService.saveAssignment({
        id: 0,
        studentID: +studentID,
        labProblemID: +labProblemID,
        grade: +grade
      }
    )
      .subscribe(assignment => {
        if (assignment.id == null) {
          console.log("saved assignment ", assignment);
          this.statusString = "Assignment saved!";
        } else {
          console.log("assignment not saved");
          this.statusString = "Assignment not saved! Student ID or lab problem ID doesn't exists, or assignment already added. Please check the assignments list!"
        }
      })
  }

  goBack() {
    this.router.navigate(['/assignments']);
  }

  validateInput(studentID: string, labProblemID: string, grade: string): boolean {
    console.log("validateInput() entered", studentID, labProblemID, grade);
    this.statusString = '';
    if (studentID.trim().length == 0) {
      this.statusString += 'Invalid student ID. ';
    } else {
      const studentIDValue = +studentID;
      if (!Number.isNaN(studentIDValue)) {
        if (studentIDValue < 0) {
          this.statusString += 'Invalid student ID, must be a positive value. ';
        }
        if (!Number.isInteger(studentIDValue)) {
          this.statusString += 'Invalid student ID, must be an integer.';
        }
      } else {
        this.statusString += 'Invalid student ID, it must be a number. ';
      }
    }
    if (labProblemID.trim().length == 0) {
      this.statusString += 'Invalid lab problem ID. ';
    } else {
      const labProblemIDValue = +labProblemID;
      if (!Number.isNaN(labProblemIDValue)) {
        if (labProblemIDValue < 0) {
          this.statusString += 'Invalid lab problem ID, must be a positive value. ';
        }
        if (!Number.isInteger(labProblemIDValue)) {
          this.statusString += 'Invalid lab problem ID, must be an integer.';
        }
      } else {
        this.statusString += 'Invalid lab problem ID, it must be a number. ';
      }
    }

    if (grade.trim().length == 0) {
      this.statusString += 'Invalid grade, must have a value. ';
    } else {
      const gradeValue = +grade;
      if (!Number.isNaN(gradeValue)) {
        if (gradeValue < 0) {
          this.statusString += 'Invalid grade, must be a positive value. ';
        }
        if (!Number.isInteger(gradeValue)) {
          this.statusString += 'Invalid grade, must be an integer.';
        }
        if (gradeValue < 0 || gradeValue > 10) {
          this.statusString += 'Invalid grade, must be between 0 and 10.';
        }

      } else {
        this.statusString += 'Invalid grade, it must be a number. ';
      }
    }
    if (this.statusString.length != 0) {
      console.log("validateInput() fail");
      return false;
    }
    console.log("validateInput() success");
    return true;
  }
}
