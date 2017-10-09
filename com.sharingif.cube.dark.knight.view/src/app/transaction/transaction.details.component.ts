import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import 'rxjs/add/operator/switchMap';


const headers = new HttpHeaders().set("Content-Type", "application/json");

@Component({
  selector: 'transaction',
  templateUrl: './transaction.details.component.html',
})

export class TransactionDetailsComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {};

  transUniqueId : string;
  transactionList = null;

  queryDetail() : void {

    this.http
      .get('http://127.0.0.1:8080/dark-knight-analysis/transaction/details/'+this.transUniqueId, {headers})
      .subscribe(
        res => {
          this.transactionList = res["_data"];
          console.log(res);
        },
        err => {
          console.log("Error occured");
        });
  };

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.transUniqueId = params.transUniqueId;
      this.queryDetail();
    });
  }
}
