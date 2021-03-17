import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-assignments',
  templateUrl: './assignments.component.html',
  styleUrls: ['./assignments.component.css']
})
export class AssignmentsComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  addNewAssignment() {
    console.log("Add new assignment button clicked");
    this.router.navigate(["assignments/new"])
  }

  goToFilter() {
    console.log("Go to filter button clicked");
    this.router.navigate(["assignments/filter"]);

  }

  goToSort() {
    console.log("Go to sort button clicked");
    this.router.navigate(["assignments/sorted"]);
  }

  goToHome() {
    console.log("Go to home button clicked");
    this.router.navigate(["home"]);
  }


}
