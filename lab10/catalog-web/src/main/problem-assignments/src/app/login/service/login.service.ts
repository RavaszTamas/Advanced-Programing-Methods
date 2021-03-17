import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'}),
    withCredentials: true,observe:'response'
  };
  private url = 'http://localhost:8079/api/login';

  public currentUserRole = "NO_ROLE";
  private isTeacherVariable: boolean = false;

  constructor(
    private http: HttpClient,
    private router: Router) {
  }

  public login(username: string, password: string): Observable<boolean> {
    // @ts-ignore
    return this.http.post(this.url + "?username=" + username + "&password=" + password, {}, this.httpOptions)
      .pipe(
        tap(result => console.log(result)),
        map(result => {
            this.getCurrentUserRole().subscribe(result => {
            console.log("Got roles",result);
            //@ts-ignore
            this.currentUserRole = result.role[0];
            console.log(this.currentUserRole,"current user")
          })
            return result["status"] === 200;
        })
      );
  }

  getCurrentUserRole(): Observable<Array<string>>{

    return this.http.get<Array<string>>( "http://localhost:8079/api/user", {withCredentials:true} );
  }

  // obtainTheUseRole(){
  //   this.getCurrentUserRole().subscribe(result => {
  //     console.log("Got roles",result);
  //     //@ts-ignore
  //     if(result.role.filter(e=>e === "ROLE_TEACHER").length > 0)
  //       this.isTeacherVariable = true;
  //     console.log(this.isTeacherVariable,"teacher result")
  //   });
  // }

  logout() {
    console.log("logout accessed",this.url);
    const currentUrl = `http://localhost:8079/api/logout`;
    console.log("Sending logout",currentUrl);
    // @ts-ignore
    this.http.post(currentUrl, {}, this.httpOptions)
      .subscribe(result=>{console.log(result,"worked logout"), this.currentUserRole="NO_ROLE",this.isTeacherVariable=false,this.router.navigateByUrl("/login")},error => console.log(error,"not worked logout pls kill me"))
  }

  checkIfAuthenticated() {
    return this.currentUserRole !== "NO_ROLE";
  }

  getActiveRole(){
    return this.currentUserRole;
  }

  checkIfTeacher(){
    return this.isTeacherVariable;
  }
}

