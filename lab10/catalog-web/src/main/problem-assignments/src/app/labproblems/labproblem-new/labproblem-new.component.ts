import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Location} from "@angular/common";
import {LabproblemsService} from "../shared/labproblems.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-labproblem-new',
  templateUrl: './labproblem-new.component.html',
  styleUrls: ['./labproblem-new.component.css']
})
export class LabproblemNewComponent implements OnInit {
  statusString = 'Not submitted';
  @Output() addedNewEntityEvent = new EventEmitter();
  submitted = false;
  labProblemForm: FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private labProblemsService: LabproblemsService,
    private location: Location,
    private router: Router
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

  }


  saveLabProblem(description: string, problemNumber: string) {
    this.submitted = true;
    console.log("saveLabProblem() entered", description, problemNumber);
    if (this.labProblemForm.invalid) {
      return;
    }
    if (!this.validateInput(description, problemNumber))
      return;
    this.labProblemsService.saveLabProblem({
        id: 0,
        description,
        problemNumber: +problemNumber
      }
    )
      .subscribe(labproblem => {
        console.log("lab problems saved ", labproblem);
        this.statusString = "Lab problem saved";
      });
  }

  goBack() {
    this.router.navigate(['/labproblems']);
  }

  validateInput(description: string, problemNumber: string): boolean {
    console.log("validateInput() entered", description, problemNumber);
    this.statusString = '';
    if (description.trim().length == 0) {
      this.statusString += 'Invalid description. ';
    }
    if (problemNumber.trim().length == 0) {
      this.statusString += 'Invalid problem number. ';
    }
    if (problemNumber.trim().length == 0) {
      this.statusString += 'Invalid problem number, must have a value. ';
    } else {
      const problemNumberValue = +problemNumber;
      if (!Number.isNaN(problemNumberValue)) {
        if (problemNumberValue < 0) {
          this.statusString += 'Invalid problem number, must be a positive value. ';
        }
        if (!Number.isInteger(problemNumberValue)) {
          this.statusString += 'Invalid problem number, must be an integer.';
        }
      } else {
        this.statusString += 'Invalid problem number, it must be a number. ';
      }
    }
    if (this.statusString.length != 0) {
      console.log("validateInput() fail");
      return false;
    }
    console.log("validateInput() success");
    return true;
  }
}

