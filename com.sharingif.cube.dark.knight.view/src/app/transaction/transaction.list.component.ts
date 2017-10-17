import { Component, OnInit, Injectable, Input } from '@angular/core';


import { HttpRequest } from '../http/http.request';
import { HttpJsonService } from '../http/http.json.service';

import { PaginationCondition } from '../pagination/pagination.condition';
import { PaginationRepertory } from '../pagination/pagination.repertory';
import { Transaction } from './transaction';

declare var $:any;


@Component({
  selector: 'transaction',
  templateUrl: './transaction.list.component.html',
})

@Injectable()
export class TransactionListComponent implements OnInit {

  constructor(
    private http: HttpJsonService
  ) {};

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

    let superObject = this;

    let dayHttpRequest = new HttpRequest();
    dayHttpRequest.url = "transaction/list";
    dayHttpRequest.data = this.paginationCondition;
    dayHttpRequest.success = function (data) {
      superObject.paginationRepertory = data;
    };
    this.http.post(dayHttpRequest);

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
