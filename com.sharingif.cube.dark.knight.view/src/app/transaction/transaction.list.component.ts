import { Component, OnInit, Injectable, Input } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { PaginationCondition } from '../pagination/pagination.condition';
import { PaginationRepertory } from '../pagination/pagination.repertory';
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

  transTypeList = [
    {"name": "transactionBegin", "value": "transactionBegin"},
    {"name": "transactionError", "value": "transactionError"}
  ];

  @Input() trans: Transaction = new Transaction();

  paginationCondition : PaginationCondition<Transaction> = new PaginationCondition<Transaction>();
  paginationRepertory : PaginationRepertory<Transaction> = new PaginationRepertory<Transaction>();


  transactionList = null;

  queryList() : void {
    this.paginationCondition.condition = this.trans;

    this.http
      .post('http://127.0.0.1:8080/dark-knight-analysis/transaction/list', this.paginationCondition,{headers})
      .subscribe(
        res => {
          this.paginationRepertory = res["_data"];
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

  first() : void {
    this.paginationCondition.currentPage = 1;
    this.queryList();
  }

  prev() : void {
    if(this.paginationCondition.currentPage <= 1) {
      return;
    };
    this.paginationCondition.currentPage = this.paginationCondition.currentPage-1;
    this.queryList();
  }

  next() : void {
    if(this.paginationCondition.currentPage>=this.paginationRepertory.totalPage) {
      return;
    }
    this.paginationCondition.currentPage = this.paginationCondition.currentPage+1;
    this.queryList();
  }

  last() : void {
    this.paginationCondition.currentPage = this.paginationRepertory.totalPage;
    this.queryList();
  }

  reset() : void {
    this.trans = new Transaction();
    this.trans.transType = this.transTypeList[0].value;
  }

  ngOnInit(): void {
    this.trans.transType = this.transTypeList[0].value;
    this.paginationCondition.currentPage = 1;
    this.paginationCondition.pageSize = 10;

    this.queryList();
  };

}
