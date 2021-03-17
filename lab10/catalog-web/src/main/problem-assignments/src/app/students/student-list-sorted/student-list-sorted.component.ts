import {Component, OnInit} from '@angular/core';
import {SortDto, SortColumnDto, Student} from "../shared/student.model";
import {FormGroup} from "@angular/forms";
import {StudentService} from "../shared/student.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-student-list-sorted',
  templateUrl: './student-list-sorted.component.html',
  styleUrls: ['./student-list-sorted.component.css']
})
export class StudentListSortedComponent implements OnInit {

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


  parseSortString(sortString: string): SortDto {
    console.log("String parsing begun", sortString);
    var params = sortString.split(",");
    var result: SortDto = {sortColumnDtoList: []} as SortDto;
    try {
      for (let i = 0; i < params.length; i = i + 2) {
        result.sortColumnDtoList.push({direction: params[i], column: params[i + 1]})
      }
    } catch (error) {
      this.errorMessage = "Invalid string input";
      return null;
    }
    this.errorMessage = "";
    return result;
  }

  getStudentsSorted(sortString: string) {

    console.log(sortString)
    const sort = this.parseSortString(sortString);
    if (sort == null)
      return;

    console.log("getting students sorted", sort);
    this.studentService.getStudentsSorted(sort).subscribe(
      students => {
        console.log(students);
        this.students = students.students;
      },
      error => {
        this.errorMessage = "Invalid sort criterias";
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
