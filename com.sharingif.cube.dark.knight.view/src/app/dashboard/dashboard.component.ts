import {Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { TransactionVolumeDay } from '../transaction/transaction.volume.day';
import { TransactionDay } from '../transaction/transaction.day';
import { TransactionDateTimeStatistics } from '../transaction/transaction.datetime.statistics';
import { TransactionStatistics } from '../transaction/transaction.statistics';

const headers = new HttpHeaders().set("Content-Type", "application/json");

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
})

export class DashboardComponent implements OnInit {

  constructor(
    private http: HttpClient
  ) {};

  transactionVolumeDay : TransactionVolumeDay = new TransactionVolumeDay();
  transactionDay : TransactionDay = new TransactionDay();
  transactionDateTimeStatisticsArray : Array<TransactionDateTimeStatistics> = new Array<TransactionDateTimeStatistics>();
  transactionStatisticsArray : Array<TransactionStatistics> = new Array<TransactionStatistics>();

  // lineChart
  public lineChartLabels: Array<number> = new Array();
  public lineChartData: Array<number> = new Array();
  public lineChartColors: Array<any> = [
    {
      backgroundColor: 'rgba(12,194,170,0.2)',
      borderColor: 'rgba(12,194,170,1.0)',
      fontColor: '#FFFFFF',
      borderWidth: 2,
      pointBackgroundColor: 'rgba(255,255,255,1.0)',
      pointBorderColor: 'rgba(12,194,170,1.0)',
      pointHoverBackgroundColor: 'rgba(12,194,170,0.2)',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'

    }
  ];
  public lineChartType:string = 'line';

  public lineChartOptions:any = {
    responsive: true,
    isDataAvailable: true,
    scales: {
      yAxes: [{
        ticks: {
          fontColor: 'rgba(255,255,255,0.7)',
        }
      }],
      xAxes: [{
        ticks: {
          fontColor: 'rgba(255,255,255,0.87)',
        }
      }]
    },
  };

  public barChartOptions:any = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public barChartLabels: Array<string> = new Array();
  public barChartData:  Array<any>= new Array();
  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;

  // events
  public chartClicked(e:any):void {
    console.log(e);
  }

  public chartHovered(e:any):void {
    console.log(e);
  }

  ngOnInit(): void {
    this.http
      .get('http://127.0.0.1:9300/dark-knight-analysis/transaction/volume/day', {headers})
      .subscribe(
        res => {
          this.transactionVolumeDay = res["_data"];
        },
        err => {
          console.log("Error occured");
        });

    this.http
      .get('http://127.0.0.1:9300/dark-knight-analysis/transaction/day', {headers})
      .subscribe(
        res => {
          this.transactionDay = res["_data"];
        },
        err => {
          console.log("Error occured");
        });

    this.http
      .get('http://127.0.0.1:9300/dark-knight-analysis/transaction/statistics/day/hour', {headers})
      .subscribe(
        res => {
          this.transactionDateTimeStatisticsArray = res["_data"];

          for(let transactionDateTimeStatistics  of this.transactionDateTimeStatisticsArray) {
            this.lineChartLabels.push(transactionDateTimeStatistics.hour);
            this.lineChartData.push(transactionDateTimeStatistics.count);
          }
          this.lineChartLabels = this.lineChartLabels.slice();
          this.lineChartData = this.lineChartData.slice();
        },
        err => {
          console.log("Error occured");
        });

    this.http
      .get('http://127.0.0.1:9300/dark-knight-analysis/transaction/statistics/day/transId', {headers})
      .subscribe(
        res => {
          this.transactionStatisticsArray = res["_data"];

          let chartData = new Array<number>();
          for(let transactionStatistics  of this.transactionStatisticsArray) {
            this.barChartLabels.push(transactionStatistics.transId.replace("/api/", ""));
            this.barChartData.push(transactionStatistics.count);
          }
          this.barChartLabels = this.barChartLabels.slice();
          this.barChartData = this.barChartData.slice();
        },
        err => {
          console.log("Error occured");
        });
  };

}
