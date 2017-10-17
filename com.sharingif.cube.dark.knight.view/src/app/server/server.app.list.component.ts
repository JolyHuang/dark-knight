import { Component, OnInit } from '@angular/core';

import { HttpRequest } from '../http/http.request';
import { HttpJsonService } from '../http/http.json.service';

import {ServerApp} from "./server.app";


@Component({
  selector: 'server',
  templateUrl: './server.app.list.component.html',
})

export class ServerAppListComponent implements OnInit {

  constructor(
    private http: HttpJsonService
  ) {};

  serverAppList: Array<ServerApp>;

  ngOnInit(): void {

    let superObject = this;

    let dayHttpRequest = new HttpRequest();
    dayHttpRequest.url = "serverApp/list";
    dayHttpRequest.success = function (data) {
      superObject.serverAppList = data;
    };
    this.http.get(dayHttpRequest);

  };

}

