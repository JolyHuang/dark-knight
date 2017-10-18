import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { HttpRequest } from '../http/http.request';
import { HttpJsonService } from '../http/http.json.service';

import 'rxjs/add/operator/switchMap';



@Component({
  selector: 'transaction',
  templateUrl: './transaction.details.component.html',
})

export class TransactionDetailsComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private http: HttpJsonService
  ) {};

  transUniqueId : string;
  transactionList = null;

  queryDetail() : void {

    let superObject = this;

    let httpRequest = new HttpRequest();
    httpRequest.url = "transaction/details/"+this.transUniqueId;
    httpRequest.success = function (data) {
      superObject.transactionList = data;
    };
    this.http.get(httpRequest);

  };

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.transUniqueId = params.transUniqueId;
      this.queryDetail();
    });
  }
}
