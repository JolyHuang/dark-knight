import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

import { User } from "./user";
import { HttpRequest } from '../http/http.request';
import { HttpJsonService } from '../http/http.json.service';


@Injectable()
export class UserService {

  constructor(
    private http: HttpJsonService,
    private router: Router
  ) {};

  private user : User;

  isLogin(): boolean {
    if(this.user != null) {
      return true;
    }
    let superObject = this;

    let loginHttpRequest = new HttpRequest();
    loginHttpRequest.url = "user/user";
    loginHttpRequest.success = function (data) {
      alert("success");
      superObject.user = data;
      superObject.router.navigate(['/dashboard']);

      return true;
    };
    loginHttpRequest.failure = function (data) {
      alert("failure");
      superObject.router.navigate(['/login']);
      return false;
    };
    this.http.get(loginHttpRequest);

    return true;
  }

  login(user : User) {

    let superObject = this;

    let loginHttpRequest = new HttpRequest();
    loginHttpRequest.url = "user/login";
    loginHttpRequest.data = user;
    loginHttpRequest.success = function (data) {
      superObject.user = data;
      superObject.router.navigateByUrl("dashboard");
    };
    this.http.post(loginHttpRequest);
  }

}
