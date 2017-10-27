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
    return this.user != null;
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
