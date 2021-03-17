import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {StudentService} from "../shared/student.service";
import {Location} from "@angular/common";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-student-new',
  templateUrl: './student-new.component.html',
  styleUrls: ['./student-new.component.css']
})
export class StudentNewComponent implements OnInit {
  statusString = 'Not submitted';
  submitted = false;
  @Output() addedNewEntityEvent = new EventEmitter();
  studentForm: FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private studentService: StudentService,
    private location: Location,
    private router: Router
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

  }

  goBack() {
    this.router.navigate(['/students']);
  }

  saveStudent(serialNumber: string, name: string, groupNumber: string) {
    console.log("saveStudent() entered", serialNumber, name, groupNumber);
    this.submitted = true;
    if (this.studentForm.invalid) {
      return;
    }
    if (!this.validateInput(serialNumber, name, groupNumber))
      return;
    this.studentService.saveStudent({
        id: 0,
        serialNumber,
        name,
        groupNumber: +groupNumber
      }
    )
      .subscribe(student => {
          console.log("saved student ", student);
          this.statusString = "Student saved!";
        },
        error => {
          this.statusString = "Not saved!"
        });
    this.addedNewEntityEvent.emit();
    // this.router.navigate(['/students']);
    // this.location.back();
  }

  validateInput(serialNumber: string, name: string, groupNumber: string): boolean {
    console.log("validateInput() entered", serialNumber, name, groupNumber);
    this.statusString = '';
    if (name.trim().length == 0) {
      this.statusString += 'Invalid name. ';
    }
    if (serialNumber.trim().length == 0) {
      this.statusString += 'Invalid serial number. ';
    }
    if (groupNumber.trim().length == 0) {
      this.statusString += 'Invalid group number, must have a value. ';
    } else {
      const groupNumberValue = +groupNumber;
      if (!Number.isNaN(groupNumberValue)) {
        if (groupNumberValue < 0) {
          this.statusString += 'Invalid group number, must be a positive value. ';
        }
        if (!Number.isInteger(groupNumberValue)) {
          this.statusString += 'Invalid group number, must be an integer.';
        }
      } else {
        this.statusString += 'Invalid group number, it must be a number. ';
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
