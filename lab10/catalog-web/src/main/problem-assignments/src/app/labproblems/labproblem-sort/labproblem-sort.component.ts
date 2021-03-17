import {Component, OnInit} from '@angular/core';
import {LabProblem} from "../shared/labproblems.model";
import {Router} from "@angular/router";
import {LabproblemsService} from "../shared/labproblems.service";
import {SortDto} from "../../students/shared/student.model";

@Component({
  selector: 'app-labproblem-sort',
  templateUrl: './labproblem-sort.component.html',
  styleUrls: ['./labproblem-sort.component.css']
})
export class LabproblemSortComponent implements OnInit {

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
        this.errorMessage = "Invalid sort criterias";
        console.log('HTTP error', error)
      }
    );

  }
}
