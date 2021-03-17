import {Component, OnInit} from '@angular/core';
import {LabProblem} from "../shared/labproblems.model";
import {Router} from "@angular/router";
import {LabproblemsService} from "../shared/labproblems.service";
import {SortDto} from "../../students/shared/student.model";

@Component({
  selector: 'app-labproblem-filter',
  templateUrl: './labproblem-filter.component.html',
  styleUrls: ['./labproblem-filter.component.css']
})
export class LabproblemFilterComponent implements OnInit {

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

  parseSortString(sortString: string): SortDto {
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

  getLabProblemsSorted(sortString: string) {
    console.log(sortString)
    const sort = this.parseSortString(sortString);
    if (sort == null)
      return;

    console.log("getting students sorted", sort);
    this.labProblemService.geLabProblemsSorted(sort).subscribe(
      labproblems => {
        console.log(labproblems);
        this.labProblems = labproblems.labProblems;
      },
      error => {
        this.errorMessage = <any>error;
        console.log('HTTP error', error)
      }
    );


  }

  getLabProblemsFilteredByProblemNumber(filterValue: string) {
    let value = parseInt(filterValue);

    console.log("getting students for page", value);
    this.labProblemService.getLabProblemsFilteredByProblemNumber(value).subscribe(
      labproblems => {
        console.log(labproblems);
        this.labProblems = labproblems.labProblems;
      },
      error => {
        this.errorMessage = <any>error;
        console.log('HTTP error', error)
      }
    );

  }

  getLabProblemsFilteredByDescription(filterValue: string) {

    console.log("getting students for page", filterValue);
    this.labProblemService.getLabProblemsFilteredByDescription(filterValue).subscribe(
      labproblems => {
        console.log(labproblems);
        this.labProblems = labproblems.labProblems;
      },
      error => {
        this.errorMessage = <any>error;
        console.log('HTTP error', error)
      }
    );

  }

}

