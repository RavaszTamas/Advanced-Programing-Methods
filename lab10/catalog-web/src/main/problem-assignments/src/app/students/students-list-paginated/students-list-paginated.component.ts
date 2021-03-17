import {Component, OnInit} from '@angular/core';
import {Student} from "../shared/student.model";
import {StudentService} from "../shared/student.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-students-list-paginated',
  templateUrl: './students-list-paginated.component.html',
  styleUrls: ['./students-list-paginated.component.css']
})
export class StudentsListPaginatedComponent implements OnInit {

  students: Student[];
  studentsOriginal: Student[];
  pages: Array<number>;
  errorMessage: string;
  selectedStudent: Student;
  interval: any;
  currentPage: number;
  readonly pageSize = 5;
  searchTerm: string

  constructor(
    private studentService: StudentService,
    private router: Router
  ) {
    this.errorMessage = "No error";
  }

  ngOnInit(): void {
    this.currentPage = 0;
    this.getStudents();
    // this.interval = setInterval(() => {
    //   this.getStudents();
    // }, 500);

  }

  dostuff(){
    console.log("stuff");
  }

  getStudents() {
    console.log("getting students for page");
    this.studentService.getStudentsPaged(this.currentPage, this.pageSize).subscribe(
      studentsPage => {
        console.log(studentsPage);
        this.students = studentsPage['content'];
        this.pages = new Array(studentsPage['totalPages'])
        if (this.students.length == 0) {
          if (this.currentPage > 0) {
            this.currentPage -= 1;
            console.log("empty list, getting new entities")
            this.getStudents();
          }
        }
        console.log("list adjusted");
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

  gotoAssignments(): void {
    this.router.navigate(['/assignments/detail', this.selectedStudent.id])
  }


  deleteStudent(student: Student): void {
    console.log("deleting student: ", student)
    this.studentService.deleteStudent(student.id).subscribe(_ => {
      console.log("student deleted");
      this.students = this.students.filter(s => s.id != student.id);
      this.getStudents();

    });
  }

  setPage(i: number, event: any) {
    event.preventDefault();
    this.currentPage = i;
    this.getStudents();
  }

  sortBy(column: string) {
    this.students = this.students.sort((a, b) => a[column].localeCompare(b[column]));
  }

  sortByNumeric(column: string) {
    this.students = this.students.sort((a, b) => a[column] - (b[column]));
  }

  filterByName(value: string) {
    this.students = this.studentsOriginal.filter(s => s.name.includes(value));
  }
}
