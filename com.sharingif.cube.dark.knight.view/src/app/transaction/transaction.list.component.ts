import { Component, OnInit, Injectable, Input } from '@angular/core';

import { HttpRequest } from '../http/http.request';
import { HttpJsonService } from '../http/http.json.service';

import { PaginationCondition } from '../pagination/pagination.condition';
import { PaginationRepertory } from '../pagination/pagination.repertory';
import { Transaction } from './transaction';

declare var $:any;


@Component({
  selector: 'dashboard',
  templateUrl: './transaction.list.component.html',
})

@Injectable()
export class TransactionListComponent implements OnInit {

  constructor(
    private http: HttpJsonService
  ) {};

  transTypeList = [
    {"name": "transactionBegin", "value": "transactionBegin"},
    {"name": "transactionError", "value": "transactionError"},
    {"name": "transactionError", "value": "controllerBegin"}
  ];

  @Input() trans: Transaction = new Transaction();

  paginationCondition : PaginationCondition<Transaction> = new PaginationCondition<Transaction>();
  paginationRepertory : PaginationRepertory<Transaction> = new PaginationRepertory<Transaction>();


  transactionList = null;

  queryList() : void {
    this.paginationCondition.condition = this.trans;

    let superObject = this;

    let httpRequest = new HttpRequest();
    httpRequest.url = "transaction/list";
    httpRequest.data = this.paginationCondition;
    httpRequest.success = function (data) {
      superObject.paginationRepertory = data;
    };
    this.http.post(httpRequest);

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

    let currentDate = new Date();
    let year = currentDate.getFullYear();
    let month = currentDate.getMonth()+1;
    let day = currentDate.getDate();

    let currentDateStr = year+"-"+month+"-"+day;

    this.trans.startTimeBegin = currentDateStr+" 00:00:00";
    this.trans.startTimeEnd = currentDateStr+" 23:59:59";

    this.trans.transType = this.transTypeList[0].value;
    this.paginationCondition.currentPage = 1;
    this.paginationCondition.pageSize = 10;

    this.queryList();
  };

}
