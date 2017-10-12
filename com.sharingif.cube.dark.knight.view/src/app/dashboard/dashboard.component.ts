import {Component, OnInit, ViewChild} from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

import { TransactionVolumeDay } from '../transaction/transaction.volume.day';
import { TransactionDay } from '../transaction/transaction.day';
import { TransactionDateTimeStatistics } from '../transaction/transaction.datetime.statistics';

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

  @ViewChild('myChart') myChart

  // lineChart
  public lineChartData:Array<number> = new Array<number>();
  public lineChartLabels:Array<number> = new Array<number>();
  public lineChartType:string = 'line';

  public lineChartOptions:any = {
    responsive: true,
    isDataAvailable: true
  };
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
            this.lineChartData.push(transactionDateTimeStatistics.count);
            this.lineChartLabels.push(transactionDateTimeStatistics.hour);
          }
          // this.myChart.chart.config.data.data = this.lineChartData;
          // this.myChart.chart.config.data.labels = this.lineChartLabels;
        },
        err => {
          console.log("Error occured");
        });
  };

}
