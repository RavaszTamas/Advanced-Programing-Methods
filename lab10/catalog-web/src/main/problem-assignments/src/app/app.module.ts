
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {StudentsComponent} from './students/students.component';
import {StudentListComponent} from './students/student-list/student-list.component';
import {StudentService} from "./students/shared/student.service";
import {HttpClientModule} from "@angular/common/http";
import {LabproblemsComponent} from './labproblems/labproblems.component';
import {AssignmentsComponent} from './assignments/assignments.component';
import {LabproblemsListComponent} from './labproblems/labproblems-list/labproblems-list.component';
import {LabproblemsService} from "./labproblems/shared/labproblems.service";
import {AssignmentsListComponent} from './assignments/assignments-list/assignments-list.component';
import {AssignmentService} from "./assignments/shared/assignment.service";
import {StudentDetailComponent} from './students/student-detail/student-detail.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {StudentNewComponent} from './students/student-new/student-new.component';
import {LabproblemDetailComponent} from './labproblems/labproblem-detail/labproblem-detail.component';
import {LabproblemNewComponent} from './labproblems/labproblem-new/labproblem-new.component';
import {AssignmentDetailComponent} from './assignments/assignment-detail/assignment-detail.component';
import {AssignmentNewComponent} from './assignments/assignment-new/assignment-new.component';
import {AssignmentChangeDetailComponent} from './assignments/assignment-change-detail/assignment-change-detail.component';
import {ReportsComponent} from './reports/reports.component';
import {StudentsListPaginatedComponent} from './students/students-list-paginated/students-list-paginated.component';
import {LabproblemsListPaginatedComponent} from './labproblems/labproblems-list-paginated/labproblems-list-paginated.component';
import {AssignmentListPaginatedComponent} from './assignments/assignment-list-paginated/assignment-list-paginated.component';
import {StudentsFilterComponent} from './students/students-filter/students-filter.component';
import {StudentListSortedComponent} from './students/student-list-sorted/student-list-sorted.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LabproblemSortComponent} from './labproblems/labproblem-sort/labproblem-sort.component';
import {LabproblemFilterComponent} from './labproblems/labproblem-filter/labproblem-filter.component';
import {StudentFilterPipe} from "./students/students-list-paginated/student-filter.pipe";
import {
  LabProblemFilterPipe
} from "./labproblems/labproblems-list-paginated/lab-problemfilet.pipe";
import {AssignmentFilterPipe} from "./assignments/assignment-list-paginated/assignment-filter.pipe";
import {AssignmentSortComponent} from './assignments/assignment-sort/assignment-sort.component';
import {AssignmentFilterComponent} from './assignments/assignment-filter/assignment-filter.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import { SensorComponent } from './sensor/sensor.component';

@NgModule({
  declarations: [
    AppComponent,
    StudentsComponent,
    StudentListComponent,
    LabproblemsComponent,
    AssignmentsComponent,
    LabproblemsListComponent,
    AssignmentsListComponent,
    StudentDetailComponent,
    StudentNewComponent,
    LabproblemDetailComponent,
    LabproblemNewComponent,
    AssignmentDetailComponent,
    AssignmentNewComponent,
    AssignmentChangeDetailComponent,
    ReportsComponent,
    StudentsListPaginatedComponent,
    LabproblemsListPaginatedComponent,
    AssignmentListPaginatedComponent,
    StudentsFilterComponent,
    StudentListSortedComponent,
    LabproblemSortComponent,
    LabproblemFilterComponent,
    StudentFilterPipe,
    LabProblemFilterPipe,
    AssignmentFilterPipe,
    AssignmentSortComponent,
    AssignmentFilterComponent,
    HomeComponent,
    LoginComponent,
    SensorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatFormFieldModule
  ],
  providers: [StudentService, LabproblemsService, AssignmentService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
