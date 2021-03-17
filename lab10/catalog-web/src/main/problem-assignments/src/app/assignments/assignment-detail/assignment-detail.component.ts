import {Component, OnInit} from '@angular/core';
import {Assignment} from "../shared/assignment.model";
import {LabProblem} from "../../labproblems/shared/labproblems.model";
import {ActivatedRoute, Params, Route} from "@angular/router";
import {AssignmentService} from "../shared/assignment.service";
import {LabproblemsService} from "../../labproblems/shared/labproblems.service";
import {switchMap} from "rxjs/operators";
import {Student} from "../../students/shared/student.model";
import {StudentService} from "../../students/shared/student.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-assignment-detail',
  templateUrl: './assignment-detail.component.html',
  styleUrls: ['./assignment-detail.component.css']
})
export class AssignmentDetailComponent implements OnInit {

  id: Number;
  assignmentsForStudent: Assignment[];
  allTheLabProblemsAvailable: LabProblem[];
  allLabProblems: Array<LabProblem>;
  selectedAssignment: Assignment;
  errorMessage: string;
  statusString = 'Not submitted';

  constructor(
    private route: ActivatedRoute,
    public assignmentService: AssignmentService,
    public labProblemService: LabproblemsService,
    public studentService: StudentService,
    private location: Location
  ) {
  }

  ngOnInit() {
    this.getStudent();
    this.getLabProblems();
    this.getAllLabProblems();
  }

  private getAllLabProblems() {
    this.labProblemService.getLabProblems().subscribe(labproblems => this.allLabProblems = labproblems.labProblems);
  }

  getLabProblem(id: number): LabProblem {
    console.log('getLabProblem() entered', id);
    var i;
    var indexToFind;
    for (i = 0; i < this.allLabProblems.length; i++) {
      if (this.allLabProblems[i].id == id)
        indexToFind = i;
    }
    console.log('found index', indexToFind);
    let result = this.allLabProblems[indexToFind];
    console.log('result', result)
    if (result == undefined) {
      return {id: -1, description: '', problemNumber: -1};
    }
    return result;
  }

  getStudent(): void {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.route.params.pipe(switchMap((params: Params) => this.studentService.getStudent(+params['id'])))
      .subscribe(student => {
        this.assignmentService.getAssignmentsForStudent(this.id).subscribe(
          assignments => {
            this.assignmentsForStudent = assignments.assignments
          },
          error => {
            this.errorMessage = <any>error;
            console.log(error)
          }
        );
      });
  }

  getLabProblems(): void {
    this.assignmentService.getAvailableLabProblemsForStudent(this.id).subscribe(
      labProblems => {
        this.allTheLabProblemsAvailable = labProblems.labProblems
      },
      error => {
        this.errorMessage = <any>error;
        console.log('Http error', error)
      }
    );

  }

  addSelectedLabProblem(labProblem: LabProblem): void {
    this.assignmentService.saveAssignment(
      {
        id: 0,
        studentID: this.id,
        labProblemID: labProblem.id,
        grade: null
      }
    )
      .subscribe(assignment => {
        console.log("assignment student ", assignment);
        window.location.reload();
      })
  }

  submitGrade(grade: string) {
    console.log("submitGrade() entered", grade);
    if (this.validateInput(grade)) {
      this.selectedAssignment.grade = +grade;
      this.assignmentService.updateAssignment(this.selectedAssignment).subscribe(_ => {
          this.selectedAssignment = null;
          console.log("submitGrade() completed", grade);
        },
        error => {
          this.errorMessage = <any>error;
          console.log(error)
        }
      );
    }
    console.log("submitGrade() finished", grade);
  }

  selectAssignment(assignment: Assignment) {
    this.selectedAssignment = assignment;
  }

  validateInput(grade: string): boolean {
    console.log("validateInput() entered", grade);
    this.statusString = '';

    if (grade.trim().length == 0) {
      this.statusString += 'Invalid problem number. ';
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

    // if(grade == null){
    //   this.statusString += 'Invalid grade, must have a value. ';
    // }else if (!Number.isNaN(grade.valueOf())) {
    //   if (grade < 0) {
    //     this.statusString += 'Invalid grade, must be a positive value. ';
    //   }
    //   if (!Number.isInteger(grade.valueOf())) {
    //     this.statusString += 'Invalid grade, must be an integer.';
    //   }
    //   if (grade.valueOf() < 0 || grade.valueOf() > 10) {
    //     this.statusString += 'Invalid grade, must be between 0 and 10.';
    //   }
    // } else {
    //   this.statusString += 'Invalid grade, it must be a number. ';
    // }
    //
    // if (this.statusString.length != 0) {
    //   console.log("validateInput() fail");
    //   return false;
    // }
    // console.log("validateInput() success");
    // return true;
  }

  deleteAssignment(assignment: Assignment): void {
    console.log("deleteAssignment() entered: ", assignment)
    this.assignmentService.deleteAssignment(assignment.id).subscribe(_ => {
      console.log("deleteAssignment() exit");
      window.location.reload();
    });
  }


}
