import {Component, Input, OnInit} from '@angular/core';
import {LabProblem} from "../shared/labproblems.model";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from "@angular/common";
import {LabproblemsService} from "../shared/labproblems.service";
import {switchMap} from "rxjs/operators";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-labproblem-detail',
  templateUrl: './labproblem-detail.component.html',
  styleUrls: ['./labproblem-detail.component.css']
})
export class LabproblemDetailComponent implements OnInit {

  @Input() labProblem: LabProblem;

  statusString = 'Not submitted';
  submitted = false;
  labProblemForm: FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private labProblemService: LabproblemsService,
    private route: ActivatedRoute,
    private location: Location
  ) {
  }

  get getLabProblemForm() {
    return this.labProblemForm.controls;
  }

  ngOnInit(): void {

    this.labProblemForm = this.formBuilder.group({
      description: ['', [Validators.required]],
      problemNumber: ['', [Validators.required, Validators.min(0), Validators.pattern("^[A-Za-z0-9]+$")]],
    });


    this.route.params.pipe(switchMap((params: Params) => this.labProblemService.getLabProblem(+params['id'])))
      .subscribe(student => this.labProblem = student)
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.submitted = true;
    if (this.labProblemForm.invalid) {
      return;
    }
    if (!this.validateInput())
      return;
    console.log("updating lab problem ", this.labProblem);
    this.labProblemService.updateLabProblem(this.labProblem).subscribe(_ => {
      console.log("lab problem update finished");
      this.statusString = "Successfully updated!";
    });
  }

  validateInput(): boolean {
    console.log("validateInput() entered ", this.labProblem);
    this.statusString = '';
    if (this.labProblem.description.trim().length == 0) {
      this.statusString += 'Invalid description. ';
    }
    if (this.labProblem.problemNumber < 0) {
      this.statusString += 'Invalid problem number, must be a positive value. ';
    }
    if (!Number.isInteger(this.labProblem.problemNumber)) {
      this.statusString += 'Invalid problem number, must be an integer.';
    }
    if (this.statusString.length != 0) {
      console.log("validateInput() fail");
      return false;
    }
    console.log("validateInput() success");
    return true;
  }


}
