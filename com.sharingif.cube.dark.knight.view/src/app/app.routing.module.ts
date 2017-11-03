import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';

import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ServerAppListComponent } from './server/server.app.list.component'
import { TransactionListComponent } from './transaction/transaction.list.component';
import { TransactionDetailsComponent } from './transaction/transaction.details.component';
import { AuthCanActivate } from './user/auth.can.activate'
import { UserLoginComponent } from './user/user.login.component'


const appRoutes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthCanActivate],
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate:[AuthCanActivate],
      },
      {
        path: 'serverAppList',
        component: ServerAppListComponent,
        canActivate:[AuthCanActivate],
      },
      {
        path: 'transactionList',
        component: TransactionListComponent,
        canActivate:[AuthCanActivate],
      },
      {
        path: 'transactionDetails/:transUniqueId/:startTime',
        component: TransactionDetailsComponent,
        canActivate:[AuthCanActivate],
      },
    ]
  },
  {
    path: 'login',
    component: UserLoginComponent,
  },
  {
    path: '',
    redirectTo: 'home/dashboard',
    pathMatch: 'full'
  }

];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false } // <-- debugging purposes only
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
