import {Component, OnInit } from '@angular/core';

import { HttpRequest } from '../http/http.request';
import { HttpJsonService } from '../http/http.json.service';

import { TransactionVolumeDay } from '../transaction/transaction.volume.day';
import { TransactionDay } from '../transaction/transaction.day';
import { TransactionDateTimeStatistics } from '../transaction/transaction.datetime.statistics';
import { TransactionStatistics } from '../transaction/transaction.statistics';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
})

export class DashboardComponent implements OnInit {

  constructor(
    private http: HttpJsonService
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
    responsive: true,
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
    }
  };
  public barChartLabels: Array<string> = new Array();
  public barChartData:  Array<any>= new Array();
  public barChartType:string = 'bar';

  public barChartColors: Array<any> = [
    {
      backgroundColor: 'rgba(54,162,235,0.8)'
    }
  ];

  // events
  public chartClicked(e:any):void {
    console.log(e);
  }

  public chartHovered(e:any):void {
    console.log(e);
  }

  ngOnInit(): void {

    let superObject = this;

    let volumeDayHttpRequest = new HttpRequest();
    volumeDayHttpRequest.url = "transaction/volume/day";
    volumeDayHttpRequest.success = function (data) {
      superObject.transactionVolumeDay = data;
    };
    this.http.get(volumeDayHttpRequest);

    let dayHttpRequest = new HttpRequest();
    dayHttpRequest.url = "transaction/day";
    dayHttpRequest.success = function (data) {
      superObject.transactionDay = data;
    };
    this.http.get(dayHttpRequest);

    let dayHourHttpRequest = new HttpRequest();
    dayHourHttpRequest.url = "transaction/statistics/day/hour";
    dayHourHttpRequest.success = function (data) {
      superObject.transactionDateTimeStatisticsArray = data;

      for(let transactionDateTimeStatistics  of superObject.transactionDateTimeStatisticsArray) {
        superObject.lineChartLabels.push(transactionDateTimeStatistics.hour);
        superObject.lineChartData.push(transactionDateTimeStatistics.count);
      }
      superObject.lineChartLabels = superObject.lineChartLabels.slice();
      superObject.lineChartData = superObject.lineChartData.slice();
    };
    this.http.get(dayHourHttpRequest);

    let dayTransIdHttpRequest = new HttpRequest();
    dayTransIdHttpRequest.url = "transaction/statistics/day/transId";
    dayTransIdHttpRequest.success = function (data) {
      superObject.transactionStatisticsArray = data;

      for(let transactionStatistics  of superObject.transactionStatisticsArray) {
        let transId = transactionStatistics.transId.replace("/api/", "");
        if(transId.length>12) {
          transId = transId.slice(0,9)+"...";
        }
        superObject.barChartLabels.push(transId);
        superObject.barChartData.push(transactionStatistics.count);
      }
      superObject.barChartLabels = superObject.barChartLabels.slice();
      superObject.barChartData = superObject.barChartData.slice();
    };
    this.http.get(dayTransIdHttpRequest);

  };

}
