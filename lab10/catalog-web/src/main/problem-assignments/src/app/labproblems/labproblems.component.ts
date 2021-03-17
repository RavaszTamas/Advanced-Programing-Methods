import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-labproblems',
  templateUrl: './labproblems.component.html',
  styleUrls: ['./labproblems.component.css']
})
export class LabproblemsComponent implements OnInit {

  constructor(
    private router: Router
  ) {
  }

  ngOnInit(): void {

  }

  addNewLabProblem() {
    console.log("Add new lab problem button clicked");
    this.router.navigate(["labproblems/new"])
  }

  goToFilter() {
    console.log("Go to filter button clicked");
    this.router.navigate(["labproblems/filter"]);

  }

  goToSort() {
    console.log("Go to sort button clicked");
    this.router.navigate(["labproblems/sorted"]);
  }

  goToHome() {
    console.log("Go to home button clicked");
    this.router.navigate(["home"]);
  }
}
