import {Component, Input, OnInit} from '@angular/core';
import {StudentService} from "../shared/student.service";
import {ActivatedRoute, Params} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Location} from "@angular/common";
import {Student} from "../shared/student.model";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css']
})
export class StudentDetailComponent implements OnInit {

  @Input() student: Student;
  statusString = 'Not submitted';
  submitted = false;
  studentForm: FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private studentService: StudentService,
    private route: ActivatedRoute,
    private location: Location
  ) {
  }

  get getStudentForm() {
    return this.studentForm.controls;
  }


  ngOnInit(): void {
    this.studentForm = this.formBuilder.group({
      name: ['', Validators.required],
      serialNumber: ['', [Validators.required, Validators.pattern("^[A-Za-z0-9]+$")]],
      groupNumber: ['', [Validators.required, Validators.min(0), Validators.pattern("^\\d+$")]]
    });

    this.route.params.pipe(switchMap((params: Params) => this.studentService.getStudent(+params['id'])))
      .subscribe(student => this.student = student);

  }


  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submitted = true;
    if (!this.validateInput())
      return;
    if (this.studentForm.invalid) {
      return;
    }
    console.log("updating student ", this.student);
    this.studentService.updateStudent(this.student).subscribe(_ => {
      this.goBack();
    });
  }

  validateInput(): boolean {
    console.log("validateInput() entered ", this.student);
    this.statusString = '';
    if (this.student.name.trim().length == 0) {
      this.statusString += 'Invalid name. ';
    }
    if (this.student.serialNumber.trim().length == 0) {
      this.statusString += 'Invalid serial number. ';
    }
    if (this.student.groupNumber < 0) {
      this.statusString += 'Invalid group number, must be a positive value. ';
    }
    if (!Number.isInteger(this.student.groupNumber)) {
      this.statusString += 'Invalid group number, must be an integer.';
    }
    if (this.statusString.length != 0) {
      console.log("validateInput() fail");
      return false;
    }
    console.log("validateInput() success");
    return true;
  }

}
