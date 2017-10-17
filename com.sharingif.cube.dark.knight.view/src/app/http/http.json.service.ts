import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import {HttpRequest} from "./http.request";


const headers = new HttpHeaders().set("Content-Type", "application/json");

@Injectable()
export class HttpJsonService {

  constructor(
    private http: HttpClient
  ) {};

  ip: string = "127.0.0.1";
  port: number = 9300;
  address: string;
  useHttps: boolean;
  contextPath: string = "dark-knight-analysis";

  handleHttpRequest(httpRequest: HttpRequest) {
    if(httpRequest.ip == null) {
      httpRequest.ip = this.ip
    }
    if(httpRequest.port == null) {
      httpRequest.port = this.port
    }
    if(httpRequest.address == null) {
      httpRequest.address = this.address
    }
    if(httpRequest.useHttps == null) {
      httpRequest.useHttps = this.useHttps
    }
    if(httpRequest.contextPath == null) {
      httpRequest.contextPath = this.contextPath
    }
  }

  get(httpRequest: HttpRequest): any {

    this.handleHttpRequest(httpRequest);

    this.http
      .get(httpRequest.getFullUrl(), {headers})
      .subscribe(
        response => {
          if(response["_tranStatus"]) {
            if(typeof httpRequest.success == "function"){
              httpRequest.success(response["_data"]);
            };

          } else {
            response => httpRequest.failure;
          }
        },
        err => {
          console.log(err);
        });
  }

  post(httpRequest: HttpRequest): any {
    this.http
      .post(httpRequest.getFullUrl(), httpRequest.data,{headers})
      .subscribe(
        response => {
          if(response["_tranStatus"]) {
            if(typeof httpRequest.success == "function"){
              httpRequest.success(response["_data"]);
            };

          } else {
            response => httpRequest.failure;
          }
        },
        err => {
          console.log(err);
        });
  }


}
