import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Track} from "../dto/Track";
import {ChartDataSets, ChartOptions} from "chart.js";
import {TrackService} from "../track/track.service";
import {Color} from "ng2-charts";
import {PluginServiceGlobalRegistrationAndOptions} from "ng2-charts/lib/base-chart.directive";
import * as pluginCrosshair from 'chartjs-plugin-crosshair'
import {TrackPoint} from "../dto/TrackPoint";

@Component({
  selector: 'app-heightprofile',
  templateUrl: './heightprofile.component.html',
  styleUrls: ['./heightprofile.component.css']
})

export class HeightprofileComponent implements OnInit {

  private _track: Track;

  constructor(private trackService: TrackService) {  }

  @Output() trackPointSelected = new EventEmitter<TrackPoint>();

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
      this.trackPoints = data;
      let elevationData = data.map((trackPoint) => {
          return {x: trackPoint.time,
            y: trackPoint.elevation,
            trackPoint: trackPoint
          };
        }
      );

      let avgSpeedData = data.map((trackPoint) => {
        return {x: trackPoint.time,
          y: trackPoint.avgSpeedToSuccessor
        }
      });

      this.lineChartData = [{
        data: elevationData,
        label: '',
        lineTension: 0.2,
        pointRadius: 0,
        borderWidth: 0,
        backgroundColor: 'rgb(100,100,100)',
        borderColor: 'rgb(100,100,100)',
        yAxisID: 'elevationAxis'
      },{
        data: avgSpeedData,
        label: '',
        lineTension: 0.2,
        pointRadius: 0,
        borderWidth: 0,
        borderColor: 'rgba(0,100,0,1.0)',
        yAxisID: 'speedAxis',
        fill: false
      }
      ];
    })

  }

  onHover = (event: MouseEvent, activeElements: Array<any>) : any => {
    if (activeElements.length > 0) {
      this.trackPointSelected.emit(this.trackPoints[activeElements[0]._index]);
    }
  }

  public lineChartData: ChartDataSets[] = [ ];
  private trackPoints: TrackPoint[] = [];

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
        position: 'right',
        gridLines: {display: false}
      },{
        scaleLabel: {display: true, labelString: 'km/h'},
        //gridLines: {display: false},
        display: true,
        id: 'speedAxis'
      } ]
    },
    plugins: {
      crosshair: {
        line: {
          color: '#F66',        // crosshair line color
          width: 1,             // crosshair line width
          dashPattern: [5, 5]   // crosshair line dash pattern
        }
      }
    },
    hover: {
      mode: "x",
      intersect: false
    },
    onHover : this.onHover,
    events: ['mousemove']
  }

  public chartPlugins: PluginServiceGlobalRegistrationAndOptions[] = [pluginCrosshair];

  public lineChartColors: Color[] = [
    {
      borderColor: 'rgba(0,0,0,0.3)',
      backgroundColor: 'rgba(0,0,0,0.3)',
    },
  ];

  ngOnInit(): void {

  }
}
