import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {LoginService} from "../login/service/login.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isTeacherVariable=false;
  title = 'Demo';
  greeting = {};
  private url = 'http://localhost:8079/logout';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'}),
    withCredentials: true,observe:'response'
  };

  constructor( private http: HttpClient, private router: Router, private loginService: LoginService) {
  }


  ngOnInit(): void {
    this.loginService.getCurrentUserRole().subscribe(result => {
      console.log("Got roles",result);
      //@ts-ignore
      if(result.role.filter(e=>e === "ROLE_TEACHER").length > 0)
        this.isTeacherVariable = true;
      console.log(this.isTeacherVariable,"teacher result")
    });
  }

  logout() {
    console.log("logout accessed");
    // @ts-ignore
    this.loginService.logout();
  }

  isTeacher() {
    return this.isTeacherVariable;
  }
}
