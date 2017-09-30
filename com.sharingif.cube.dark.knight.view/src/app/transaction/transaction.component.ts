import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Injectable }    from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import 'rxjs/add/operator/toPromise';


const headers = new HttpHeaders().set("Content-Type", "application/json");

@Component({
  selector: 'transaction',
  templateUrl: './transaction.component.html',
})

@Injectable()
export class TransactionComponent implements OnInit {

  constructor(private http: HttpClient) {};

  transactionList = null;

  ngOnInit(): void {
    this.http
      .post('http://127.0.0.1:8080/dark-knight-analysis/transaction/list', {},{headers})
      .subscribe(
        res => {
          this.transactionList = res["_data"];
          console.log(res);
        },
        err => {
          console.log("Error occured");
        }
      );

  }

}
