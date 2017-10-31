import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core'
import { NgDatepickerModule } from 'ng2-datepicker';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { ChartsModule } from 'ng2-charts';

import { HttpJsonService } from './http/http.json.service';
import { UserService } from './user/user.service';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';

import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar.component';
import { HeaderComponent } from './header.component';
import { ThemeSwitcherComponent } from './theme.switcher.component';
import { FooterComponent } from './footer.component';

import { AuthCanActivate } from './user/auth.can.activate'

import { DashboardComponent } from './dashboard/dashboard.component';
import { ServerAppListComponent } from './server/server.app.list.component'
import { TransactionListComponent } from './transaction/transaction.list.component';
import { TransactionDetailsComponent } from './transaction/transaction.details.component';
import { UserLoginComponent } from './user/user.login.component'


@NgModule({
  declarations: [
    AppComponent,
    UserLoginComponent,
    HomeComponent,
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
    AppRoutingModule
  ],
  providers: [
    HttpJsonService,
    AuthCanActivate,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
