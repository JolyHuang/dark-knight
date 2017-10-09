import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core'
import { NgDatepickerModule } from 'ng2-datepicker';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar.component';
import { HeaderComponent } from './header.component';
import { ThemeSwitcherComponent } from './theme.switcher.component';
import { FooterComponent } from './footer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TransactionListComponent } from './transaction/transaction.list.component';
import { TransactionDetailsComponent } from './transaction/transaction.details.component';


const appRoutes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'transaction',
    component: TransactionListComponent,
  },
  {
    path: 'transactionDetails/:transUniqueId',
    component: TransactionDetailsComponent,
  },
  { path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HeaderComponent,
    ThemeSwitcherComponent,
    FooterComponent,
    DashboardComponent,
    TransactionListComponent,
    TransactionDetailsComponent
  ],
  imports: [
    BrowserModule,
    NgDatepickerModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false } // <-- debugging purposes only
    )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
