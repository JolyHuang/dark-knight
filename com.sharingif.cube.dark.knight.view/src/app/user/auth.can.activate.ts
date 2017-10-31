import { Injectable }     from '@angular/core';
import { Router, CanActivate }    from '@angular/router';

import { UserService } from './user.service';

@Injectable()
export class AuthCanActivate implements CanActivate {

  constructor(
    private uerService: UserService,
    private router: Router
  ) {};

  canActivate() {
    if(this.uerService.isLogin()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }

}
