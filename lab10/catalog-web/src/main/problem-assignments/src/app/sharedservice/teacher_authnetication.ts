import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {LoginService} from "../login/service/login.service";

@Injectable({providedIn: 'root'})
export class TeacherAuthenticationGuard implements CanActivate {
  constructor(
    private router: Router,
    private loginService: LoginService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log("teacher authentication guard " + this.loginService.currentUserRole);
    if (this.loginService.getActiveRole() !== "ROLE_TEACHER") {
      this.router.navigateByUrl("/home");
      return false;
    } else {
      return true;
    }
  }
}
