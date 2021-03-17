import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {StudentsComponent} from "./students/students.component";
import {LabproblemsComponent} from "./labproblems/labproblems.component";
import {AssignmentsComponent} from "./assignments/assignments.component";
import {StudentDetailComponent} from "./students/student-detail/student-detail.component";
import {StudentNewComponent} from "./students/student-new/student-new.component";
import {LabproblemNewComponent} from "./labproblems/labproblem-new/labproblem-new.component";
import {LabproblemDetailComponent} from "./labproblems/labproblem-detail/labproblem-detail.component";
import {AssignmentDetailComponent} from "./assignments/assignment-detail/assignment-detail.component";
import {AssignmentNewComponent} from "./assignments/assignment-new/assignment-new.component";
import {AssignmentChangeDetailComponent} from "./assignments/assignment-change-detail/assignment-change-detail.component";
import {StudentsFilterComponent} from "./students/students-filter/students-filter.component";
import {ReportsComponent} from "./reports/reports.component";
import {StudentListSortedComponent} from "./students/student-list-sorted/student-list-sorted.component";
import {LabproblemFilterComponent} from "./labproblems/labproblem-filter/labproblem-filter.component";
import {LabproblemSortComponent} from "./labproblems/labproblem-sort/labproblem-sort.component";
import {AssignmentFilterComponent} from "./assignments/assignment-filter/assignment-filter.component";
import {AssignmentSortComponent} from "./assignments/assignment-sort/assignment-sort.component";
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {StandardUserAuthenticationGuard} from "./sharedservice/standard_authentication";
import {TeacherAuthenticationGuard} from "./sharedservice/teacher_authnetication";


const routes: Routes = [
  {path: 'students', component: StudentsComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'students/detail/:id', component: StudentDetailComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'students/new', component: StudentNewComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'students/filter', component: StudentsFilterComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'students/sorted', component: StudentListSortedComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'labproblems', component: LabproblemsComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'labproblems/filter', component: LabproblemFilterComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'labproblems/sorted', component: LabproblemSortComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'labproblems/new', component: LabproblemNewComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'labproblems/detail/:id', component: LabproblemDetailComponent,canActivate:[StandardUserAuthenticationGuard]},
  {path: 'assignments', component: AssignmentsComponent,canActivate:[TeacherAuthenticationGuard]},
  {path: 'assignments/new', component: AssignmentNewComponent,canActivate:[TeacherAuthenticationGuard]},
  {path: 'assignments/filter', component: AssignmentFilterComponent,canActivate:[TeacherAuthenticationGuard]},
  {path: 'assignments/sorted', component: AssignmentSortComponent,canActivate:[TeacherAuthenticationGuard]},
  {path: 'assignments/detail/:id', component: AssignmentDetailComponent,canActivate:[TeacherAuthenticationGuard]},
  {path: 'assignments/updateDetail/:id', component: AssignmentChangeDetailComponent,canActivate:[TeacherAuthenticationGuard]},
  {path: 'reports', component: ReportsComponent,canActivate:[TeacherAuthenticationGuard]},
  { path: 'home', component: HomeComponent,canActivate:[StandardUserAuthenticationGuard]},
  { path: 'login', component: LoginComponent},
  { path: '**', redirectTo: 'login'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
