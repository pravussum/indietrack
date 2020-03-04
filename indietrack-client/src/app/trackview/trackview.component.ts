import * as L from 'leaflet';
import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";

@Component({
  selector: 'app-trackview',
  templateUrl: './trackview.component.html',
  styleUrls: ['./trackview.component.css']
})
export class TrackviewComponent implements OnInit {
  private mymap: L.Map;
  private trackId_;
  private routeUpdated: Observable<any>
  constructor(private http:HttpClient,
              private route: ActivatedRoute,
              private router: Router) { }

  @Input()
  set trackId(trackId: number) {
    this.trackId_ = trackId;
    this.getAndDrawTrack();
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      if(params.get('trackId')) {
        this.trackId_ = params.get('trackId');
        this.getAndDrawTrack();
      }
    });
  }

  private getAndDrawTrack() {
    this.http.get<LatLong[]>("http://localhost:8099/track/" + this.trackId_).subscribe(data => {
      console.log(data);
      if(this.mymap == undefined) {
        this.initMap(data[0].latitude, data[0].longitude);
      }
      this.drawTrack(data, 'blue');
    })
  }

  private initMap(latitude, longitude) {
    this.mymap = L.map('mapid').setView([latitude, longitude], 12);
    const osmUrl = 'http://{s}.tile.osm.org/{z}/{x}/{y}.png';
    L.tileLayer(osmUrl, {
      attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      maxZoom: 18
    }).addTo(this.mymap);
  }

  private drawTrack(data, color) {
    console.log("drawing " + data.length)
    const latLng = (data).map(this.mapToLatLng);
    const polyline = L.polyline(latLng, {color: color}).addTo(this.mymap);
    polyline.bringToBack()
  }


  private mapToLatLng(row : LatLong) : L.LatLng {
    return L.latLng(row.latitude, row.longitude)
  }

}
