import {Component, Input, OnInit, Output} from '@angular/core';
import {Track} from "../dto/Track";
import {ChartDataSets, ChartOptions} from "chart.js";
import {TrackService} from "../track/track.service";
import {Color} from "ng2-charts";

@Component({
  selector: 'app-heightprofile',
  templateUrl: './heightprofile.component.html',
  styleUrls: ['./heightprofile.component.css']
})
export class HeightprofileComponent implements OnInit {

  private _track: Track;

  constructor(private trackService: TrackService) {  }

  @Input()
  set track(track: Track) {
    this._track = track;
    this.drawHeightProfile();
  }

  get track(): Track {
    return this._track;
  }

  private drawHeightProfile() {
    if(!this._track) return;
    let currentTrack = this._track;
    this.trackService.getSimplifiedTrackPoints(currentTrack.id).subscribe(data => {
      let elevationData = data.map((trackPoint) => {
          return {x: trackPoint.time,
            y: trackPoint.elevation};
        }
      );

      let avgSpeedData = data.map((trackPoint) => {
        return {x: trackPoint.time,
          y: trackPoint.distToSuccessor
        }
      });


      console.log(elevationData);
      this.lineChartData = [{
        data: elevationData,
        label: '',
        lineTension: 0.4,
        pointRadius: 0,
        borderWidth: 0,
        backgroundColor: 'rgba(0,0,0,0.3)',
        yAxisID: 'elevationAxis'
      },{
        data: avgSpeedData,
        label: '',
        lineTension: 0.4,
        pointRadius: 0,
        borderWidth: 0,
        backgroundColor: 'rgba(0,100,0,0.3)',
        yAxisID: 'speedAxis'
      }
      ];
    })

  }

  public lineChartData: ChartDataSets[] = [ ];
  public chartOptions: ChartOptions = {
    responsive: true,
    legend: {
      display: false
    },
    scales: {
      xAxes: [{
        type: 'time',
        display: true,
        scaleLabel: {
          display: true
        }
      }],
      yAxes: [{
        scaleLabel: {display: true, labelString: 'm'},
        display: true,
        id: 'elevationAxis',
        gridLines: {display: false}
      },{
        scaleLabel: {display: true, labelString: 'km/h'},
        gridLines: {display: false},
        display: true,
        id: 'speedAxis',
        position: 'right'
      } ]
    }
  }

  public lineChartColors: Color[] = [
    {
      borderColor: 'rgba(0,0,0,0.3)',
      backgroundColor: 'rgba(0,0,0,0.3)',
    },
  ];

  ngOnInit(): void {
  }
}
