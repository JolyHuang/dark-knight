import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';


@Component({
  selector: 'transaction',
  templateUrl: './transaction.component.html',
})

@Injectable()
export class TransactionComponent implements OnInit {

  constructor(private http: Http) {}

  ngOnInit(): void {
    this.http.post("transaction/list", new Headers({'Content-Type': 'application/json'}))
      .toPromise()
      .then(response => response.json()._data);
  }

}
