import {Component, Injectable, Input, OnInit} from '@angular/core';


import { User } from "./user";
import { UserService } from "./user.service";

@Component({
  templateUrl: './user.login.component.html',
})

@Injectable()
export class UserLoginComponent implements OnInit {

  constructor(
    private userService: UserService,
  ) {};

  @Input() user: User = new User();

  login() {
    this.userService.login(this.user);
  }

  ngOnInit(): void {
    this.userService.getUser();
  };

}
