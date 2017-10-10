import { Component, OnInit, Injectable, Input } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { PaginationCondition } from '../pagination/pagination.condition';
import { Transaction } from './transaction';

declare var $:any;


const headers = new HttpHeaders().set("Content-Type", "application/json");

@Component({
  selector: 'transaction',
  templateUrl: './transaction.list.component.html',
})

@Injectable()
export class TransactionListComponent implements OnInit {

  constructor(private http: HttpClient) {};

  @Input() trans: Transaction = new Transaction();

  paginationCondition : PaginationCondition<Transaction> = new PaginationCondition<Transaction>();

  transactionList = null;

  queryList() : void {
    this.paginationCondition.condition = this.trans;
    this.paginationCondition.currentPage = 0;
    this.paginationCondition.pageSize = 10;

    this.http
      .post('http://127.0.0.1:8080/dark-knight-analysis/transaction/list', this.paginationCondition,{headers})
      .subscribe(
        res => {
          this.transactionList = res["_data"].pageItems;
          console.log(res);
        },
        err => {
          console.log("Error occured");
        });
  };

  clickQueryList() : void {

    this.trans.startTimeBegin = $("#startTimeBegin").val();
    this.trans.startTimeEnd= $("#startTimeEnd").val();

    this.queryList();
  };

  ngOnInit(): void {
    this.queryList();
  };

}
