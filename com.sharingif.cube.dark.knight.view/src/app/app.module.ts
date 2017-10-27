import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core'
import { NgDatepickerModule } from 'ng2-datepicker';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';

import { ChartsModule } from 'ng2-charts';

import { HttpJsonService } from './http/http.json.service';
import { UserService } from './user/user.service';

import { AppComponent } from './app.component';
import { UserLoginComponent } from './user/user.login.component'
import { NavbarComponent } from './navbar.component';
import { HeaderComponent } from './header.component';
import { ThemeSwitcherComponent } from './theme.switcher.component';
import { FooterComponent } from './footer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ServerAppListComponent } from './server/server.app.list.component'
import { TransactionListComponent } from './transaction/transaction.list.component';
import { TransactionDetailsComponent } from './transaction/transaction.details.component';


const appRoutes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'serverAppList',
    component: ServerAppListComponent,
  },
  {
    path: 'transactionList',
    component: TransactionListComponent,
  },
  {
    path: 'transactionDetails/:transUniqueId',
    component: TransactionDetailsComponent,
  },
  {
    path: 'login',
    component: UserLoginComponent,
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    UserLoginComponent,
    NavbarComponent,
    HeaderComponent,
    ThemeSwitcherComponent,
    FooterComponent,
    DashboardComponent,
    ServerAppListComponent,
    TransactionListComponent,
    TransactionDetailsComponent
  ],
  imports: [
    BrowserModule,
    NgDatepickerModule,
    FormsModule,
    HttpClientModule,
    ChartsModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false } // <-- debugging purposes only
    )
  ],
  providers: [
    HttpJsonService,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
