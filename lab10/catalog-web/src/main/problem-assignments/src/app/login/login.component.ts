import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from "./service/login.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Location} from "@angular/common";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  name: string;
  password: string;
  loginForm: FormGroup
  submitted = false;
  statusString = 'Not submitted';

  constructor(private loginService: LoginService, private router: Router, private location: Location,private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.pattern("^[A-Za-z0-9]+$")]]
    });

  }

  get getLoginForm() {
    return this.loginForm.controls;
  }

  goBack() {
    this.router.navigate(['/students']);
  }


  login(username:string,passwd:string) {
    this.submitted = true
    if (this.loginForm.invalid) {
      return;
    }
    console.log("validated",username,passwd)

    this.loginService.login(username, passwd).subscribe(
      result => {console.log(result);this.router.navigateByUrl("/home")},
      error => {this.statusString = "Login failed";console.log("Login failed, invalid credentials");}
    );
  }
}
