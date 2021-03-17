import {Component} from '@angular/core';
import {LoginService} from "./login/service/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Lab Problem Manager';

  constructor(private loginService: LoginService) {
  }

  isAuthenticated() {
    let result = this.loginService.checkIfAuthenticated();
    console.log("Authentication result",result);
    return result;
  }

  logout() {
    this.loginService.logout();
  }

  isTeacher() {

    let result = this.loginService.checkIfTeacher();
    console.log("Checking if teacher result",result);
    return result;
  }
}
