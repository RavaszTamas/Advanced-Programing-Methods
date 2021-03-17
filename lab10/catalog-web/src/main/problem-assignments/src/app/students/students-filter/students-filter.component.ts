import {Component, OnInit} from '@angular/core';
import {Student} from "../shared/student.model";
import {StudentService} from "../shared/student.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-students-filter',
  templateUrl: './students-filter.component.html',
  styleUrls: ['./students-filter.component.css']
})
export class StudentsFilterComponent implements OnInit {

  students: Student[];
  pages: Array<number>;
  errorMessage: string;
  selectedStudent: Student;
  interval: any;
  currentPage: number;
  readonly pageSize = 5;
  studentForm: FormGroup

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {
    this.errorMessage = "No error";
  }


  ngOnInit(): void {

  }

  getStudentsByGroupNumber(filterValue: string) {

    let value = parseInt(filterValue);

    console.log("getting students for page", value);
    this.studentService.getStudentsPagedFilteredByGroupNumber(value).subscribe(
      students => {
        console.log(students);
        this.students = students.students;
      },
      error => {
        this.errorMessage = <any>error;
        console.log('HTTP error', error)
      }
    );
  }


  getStudentsByName(filterValue: string) {


    console.log("getting students for page", filterValue);
    this.studentService.getStudentsPagedFilteredByName(filterValue).subscribe(
      students => {
        console.log(students);
        this.students = students.students;
      },
      error => {
        this.errorMessage = <any>error;
        console.log('HTTP error', error)
      }
    );
  }

  onSelect(student: Student): void {
    this.selectedStudent = student;
  }

  gotoDetail(): void {
    this.router.navigate(['/students/detail', this.selectedStudent.id])
  }


  deleteStudent(student: Student): void {
    console.log("deleting student: ", student)
    this.studentService.deleteStudent(student.id).subscribe(_ => {
      console.log("student deleted");
      this.students = this.students.filter(s => s.id != student.id);

    });
  }


}
