import {Component, OnInit} from '@angular/core';
import {Student} from "../shared/student.model";
import {StudentService} from "../shared/student.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {

  students: Student[];
  errorMessage: string;
  selectedStudent: Student;
  interval: any;

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {
    this.errorMessage = "No error";
  }

  ngOnInit(): void {
    this.getStudents();
    // this.interval = setInterval(() => {
    //   this.getStudents();
    // }, 500);

  }

  getStudents() {
    this.studentService.getStudents().subscribe(
      studentsDTO => this.students = studentsDTO.students,
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

  gotoAssignments(): void {
    this.router.navigate(['/assignments/detail', this.selectedStudent.id])
  }


  deleteStudent(student: Student): void {
    console.log("deleting student: ", student)
    this.studentService.deleteStudent(student.id).subscribe(_ => {
      console.log("student deleted");
      this.students = this.students.filter(s => s.id != student.id);
    });
  }

}
