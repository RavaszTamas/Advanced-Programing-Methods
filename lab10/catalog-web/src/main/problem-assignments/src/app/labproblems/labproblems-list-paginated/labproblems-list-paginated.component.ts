import {Component, OnInit} from '@angular/core';
import {LabProblem} from "../shared/labproblems.model";
import {Router} from "@angular/router";
import {LabproblemsService} from "../shared/labproblems.service";

@Component({
  selector: 'app-labproblems-list-paginated',
  templateUrl: './labproblems-list-paginated.component.html',
  styleUrls: ['./labproblems-list-paginated.component.css']
})
export class LabproblemsListPaginatedComponent implements OnInit {

  labProblems: LabProblem[];
  pages: Array<number>;
  errorMessage: String
  selectedLabProblem: LabProblem;
  currentPage: number;
  readonly pageSize = 5;
  searchTerm: string;

  constructor(
    private router: Router,
    private labProblemService: LabproblemsService
  ) {
  }

  ngOnInit(): void {
    this.currentPage = 0;
    this.getLabProblems()
  }

  public getLabProblems() {
    this.labProblemService.getLabProblemsPaged(this.currentPage, this.pageSize).subscribe(
      labProblemsPage => {
        this.labProblems = labProblemsPage['content'];
        this.pages = new Array(labProblemsPage['totalPages']);
        if (this.labProblems.length == 0) {
          if (this.currentPage > 0) {
            this.currentPage -= 1;
            this.getLabProblems();
          }
        }
        console.log("list adjusted");

      },
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
      this.getLabProblems();
    });
  }

  setPage(i: number, event: any) {
    event.preventDefault();
    this.currentPage = i;
    this.getLabProblems();
  }

  sortBy(column: string) {
    this.labProblems = this.labProblems.sort((a, b) => a[column].localeCompare(b[column]));
  }

  sortByNumeric(column: string) {
    this.labProblems = this.labProblems.sort((a, b) => a[column] - (b[column]));
  }
}
