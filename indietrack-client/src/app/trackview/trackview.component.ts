/// <reference path="leaflet.hg.d.ts"/>
/// <reference path="../../../node_modules/@types/d3/index.d.ts" />

import * as L from 'leaflet';
import {Component, Input, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import * as d3 from 'd3';
import 'leaflet.heightgraph';
import {TrackService} from "../track/track.service";

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
  private currentTrackPolyline: L.Polyline;
  constructor(private trackService: TrackService,
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
    this.trackService.getTrackPoints(this.trackId_).subscribe(data => {
      console.log(data);
      if(this.mymap == undefined) {
        this.initMap(data[0].latitude, data[0].longitude);
      } else {
        this.mymap.setView([data[0].latitude, data[0].longitude], 12)
      }
      this.drawTrack(data, 'blue');
    })
  }

  private initMap(latitude, longitude) {
    this.mymap = L.map('mapid');
    if(latitude && longitude) {
      this.mymap.setView([latitude, longitude], 12);
    }
    const osmUrl = 'http://{s}.tile.osm.org/{z}/{x}/{y}.png';
    L.tileLayer(osmUrl, {
      attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      maxZoom: 18
    }).addTo(this.mymap);
  }

  private drawTrack(data, color) {
    if(this.currentTrackPolyline){
      this.currentTrackPolyline.removeFrom(this.mymap);
    }
    console.log("drawing " + data.length)
    const latLng = (data).map(this.mapToLatLng);
    this.currentTrackPolyline = L.polyline(latLng, {color: color}).addTo(this.mymap);
    this.currentTrackPolyline.bringToBack()
  }


  private mapToLatLng(row : TrackPoint) : L.LatLng {
    return L.latLng(row.latitude, row.longitude)
  }

}
