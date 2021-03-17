import {Component, OnInit} from '@angular/core';
import {Student} from "./shared/student.model";
import {Router} from "@angular/router";
import {StudentsListPaginatedComponent} from "./students-list-paginated/students-list-paginated.component";
import {StudentService} from "./shared/student.service";

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit {


  constructor(
    private router: Router,
    private studentService: StudentService
  ) {
  }

  ngOnInit(): void {

  }

  addNewStudent() {
    console.log("Add new student button clicked");
    this.router.navigate(["students/new"]);
  }

  goToFilter() {
    console.log("Go to filter button clicked");
    this.router.navigate(["students/filter"]);

  }

  goToSort() {
    console.log("Go to sort button clicked");
    this.router.navigate(["students/sorted"]);
  }

  goToHome() {
    console.log("Go to home button clicked");
    this.router.navigate(["home"]);
  }
}
