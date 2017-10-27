import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from './user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  constructor(
    private uerService: UserService,
    private router: Router
  ) {};

  ngOnInit(): void {
    if(!(this.uerService.isLogin())) {
      this.router.navigateByUrl("login");
    }
  };

}
