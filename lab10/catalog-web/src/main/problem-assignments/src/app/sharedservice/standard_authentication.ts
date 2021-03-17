import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {LoginService} from "../login/service/login.service";

@Injectable({providedIn: 'root'})
export class StandardUserAuthenticationGuard implements CanActivate {
  constructor(
    private router: Router,
    private loginService: LoginService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log("guard user guard standard " + this.loginService.currentUserRole);
    if (this.loginService.getActiveRole() === "NO_ROLE") {
      this.router.navigateByUrl("/login");
      return false;
    } else {
      return true;
    }
  }
}
