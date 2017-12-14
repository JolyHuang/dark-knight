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
  startTime : string;
  transactionList = null;

  queryDetail() : void {

    let superObject = this;

    let httpRequest = new HttpRequest();
    httpRequest.url = "transaction/details/"+this.transUniqueId+"/"+this.startTime;
    httpRequest.success = function (data) {
      superObject.transactionList = data;

      for(let trans  of data) {
        let errorCause = trans["errorCause"];
        if(errorCause) {
          trans["errorCause"] = trans["errorCause"].replace(/(\n)/g,"<br/>");
          console.log(trans["errorCause"]);
        }
      }

    };
    this.http.get(httpRequest);

  };

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.transUniqueId = params.transUniqueId;
      this.startTime = params.startTime;
      this.queryDetail();
    });
  }
}
