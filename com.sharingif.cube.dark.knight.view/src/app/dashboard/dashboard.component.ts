import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { TransactionVolumeDay } from '../transaction/transaction.volume.day';
import { TransactionDay } from '../transaction/transaction.day';

const headers = new HttpHeaders().set("Content-Type", "application/json");

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
})

export class DashboardComponent implements OnInit {

  constructor(
    private http: HttpClient
  ) {};

  transactionVolumeDay : TransactionVolumeDay = new TransactionVolumeDay();
  transactionDay : TransactionDay = new TransactionDay();


  ngOnInit(): void {
    this.http
      .get('http://127.0.0.1:8080/dark-knight-analysis/transaction/volume/day', {headers})
      .subscribe(
        res => {
          this.transactionVolumeDay = res["_data"];
          console.log(res);
        },
        err => {
          console.log("Error occured");
        });

    this.http
      .get('http://127.0.0.1:8080/dark-knight-analysis/transaction/day', {headers})
      .subscribe(
        res => {
          this.transactionDay = res["_data"];
          console.log(res);
        },
        err => {
          console.log("Error occured");
        });
  };

}
