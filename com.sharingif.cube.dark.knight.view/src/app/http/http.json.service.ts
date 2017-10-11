import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';


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


  getUrlPrefix(): string {
    if(this.address !== null) {
      if(this.useHttps) {
        return "https://"+this.address+"/"+this.contextPath;
      } else {
        return "http://"+this.address+"/"+this.contextPath;
      }
    }

    if(this.useHttps) {
      return "https://"+this.ip+":"+this.port+"/"+this.contextPath;
    } else {
      return "http://"+this.ip+":"+this.port+"/"+this.contextPath;
    }
  }

  get(url: string, success: object, failure: object): any {
    this.http
      .get(this.getUrlPrefix()+url, {headers})
      .subscribe(
        res => {
          return res["_data"];
        },
        err => {
          console.log("Error occured");
        });
  }

  post(url: string, data: any): any {
    this.http
      .post(this.getUrlPrefix()+url, data,{headers})
      .subscribe(
        res => {
          return res["_data"];
        },
        err => {
          console.log("Error occured");
        });
  }


}
