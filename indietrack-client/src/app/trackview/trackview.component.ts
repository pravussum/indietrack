/// <reference path="leaflet.hg.d.ts"/>
/// <reference path="../../../node_modules/@types/d3/index.d.ts" />

import * as L from 'leaflet';
import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import * as d3 from 'd3';
import 'leaflet.heightgraph';


@Component({
  selector: 'app-trackview',
  templateUrl: './trackview.component.html',
  styleUrls: ['./trackview.component.css']
})
export class TrackviewComponent implements OnInit {
  private mymap: L.Map;
  private trackId_;
  private heightGraph;
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
    d3.select('body');
    this.route.paramMap.subscribe(params => {
      if(params.get('trackId')) {
        this.trackId_ = params.get('trackId');
        this.getAndDrawTrack();
      }
    });
  }

  private getAndDrawTrack() {
    if(this.trackId_ == undefined) {
      return;
    }
    // this.http.get<LatLong[]>("http://localhost:8099/track/" + this.trackId_).subscribe(data => {
    //   console.log(data);
    //   if(this.mymap == undefined) {
    //     this.initMap(data[0].latitude, data[0].longitude);
    //   }
    //   this.drawTrack(data, 'blue');
    // });
    this.http.get<any>("http://localhost:8099/track/" + this.trackId_ + "/geojson").subscribe(data => {
        if(this.mymap == undefined) {
          this.initMap(50.9263521, 14.2375971);
        }
      // this.heightGraph = L.control.heightgraph();
      // this.heightGraph.addTo(this.mymap);
      // this.heightGraph.addData(data);
      L.geoJSON(data).addTo(this.mymap);
      });
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
    this.mymap.setView([data[0].latitude, data[0].longitude], 12)
    polyline.bringToBack()
  }


  private mapToLatLng(row : LatLong) : L.LatLng {
    return L.latLng(row.latitude, row.longitude)
  }

}
