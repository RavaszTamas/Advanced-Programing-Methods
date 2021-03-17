import {Component, OnInit} from '@angular/core';
import {LabProblem} from "../shared/labproblems.model";
import {Router} from "@angular/router";
import {LabproblemsService} from "../shared/labproblems.service";

@Component({
  selector: 'app-labproblems-list',
  templateUrl: './labproblems-list.component.html',
  styleUrls: ['./labproblems-list.component.css']
})
export class LabproblemsListComponent implements OnInit {

  labProblems: LabProblem[]
  errorMessage: String
  selectedLabProblem: LabProblem;

  constructor(
    private router: Router,
    private labProblemService: LabproblemsService
  ) {
  }

  ngOnInit(): void {
    this.getLabProblems()
  }

  private getLabProblems() {
    this.labProblemService.getLabProblems().subscribe(
      labProblems => this.labProblems = labProblems.labProblems,
      error => {
        this.errorMessage = <any>error;
        console.log('Http error', error)
      }
    )
  }

  onSelect(labProblem: LabProblem): void {
    this.selectedLabProblem = labProblem;
  }

  gotoDetail(): void {
    this.router.navigate(['/labproblems/detail', this.selectedLabProblem.id])
  }


  deleteLabProblem(labProblem: LabProblem): void {
    console.log("deleting labProblem: ", labProblem)
    this.labProblemService.deleteLabProblem(labProblem.id).subscribe(_ => {
      console.log("labProblem deleted");
      this.labProblems = this.labProblems.filter(s => s.id != labProblem.id);
    });
  }

}
