import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import {ServerApp} from "./server.app";


const headers = new HttpHeaders().set("Content-Type", "application/json");

@Component({
  selector: 'server',
  templateUrl: './server.app.list.component.html',
})

export class ServerAppListComponent implements OnInit {

  constructor(private http: HttpClient) {};

  serverAppList: Array<ServerApp>;

  ngOnInit(): void {
    this.http
      .get('http://127.0.0.1:9300/dark-knight-analysis/serverApp/list', {headers})
      .subscribe(
        res => {
          this.serverAppList = res["_data"];

        },
        err => {
          console.log("Error occured");
        });
  };

}

