import {Component, Injectable, Input,} from '@angular/core';


import { User } from "./user";
import {UserService} from "./user.service";

@Component({
  selector: 'app-root',
  templateUrl: './user.login.component.html',
})

@Injectable()
export class UserLoginComponent {

  constructor(
    private userService: UserService,
  ) {};

  @Input() user: User = new User();

  login() {
    this.userService.login(this.user);
  }

}
