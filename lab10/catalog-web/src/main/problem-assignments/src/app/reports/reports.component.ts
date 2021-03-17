import {Component, OnInit} from '@angular/core';
import {AssignmentService} from "../assignments/shared/assignment.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  constructor(
    private router: Router,
    private assignmentService: AssignmentService
  ) {
  }

  mostAssignedId: number;
  mostAssignedCount: number;
  greatestMeanID: number;
  greatestMeanValue: number;
  averageGrade: number;

  ngOnInit(): void {
    this.getAllReports()
  }

  getAllReports() {

    this.assignmentService.getGreatestMeanStudent().subscribe(result => {
      this.greatestMeanID = result.id;
      this.greatestMeanValue = result.mean;
      console.log("greatest mean", result);
    })

    this.assignmentService.getMostAssignedLabProblem().subscribe(result => {
      this.mostAssignedId = result.id;
      this.mostAssignedCount = result.count;
      console.log("most assigned", result);
    })
    this.assignmentService.getAverageGrade().subscribe(result => {
      this.averageGrade = result;
      console.log("average grade", result)
    })

  }

  goToHome() {
    console.log("Go to home button clicked");
    this.router.navigate(["home"]);
  }
}
