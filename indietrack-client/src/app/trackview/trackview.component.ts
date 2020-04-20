/// <reference path="../../typings/leaflet-linestring-select.d.ts" />
import * as L from 'leaflet';
import 'leaflet-linestring-select'
import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TrackService} from "../track/track.service";
import {Track} from "../dto/Track";
import {GeoJSON, LatLng, LatLngBounds} from "leaflet";
import {TrackPoint} from "../dto/TrackPoint";
import {SegmentService} from "../segment/segment.service";

@Component({
  selector: 'app-trackview',
  templateUrl: './trackview.component.html',
  styleUrls: ['./trackview.component.css']
})
export class TrackviewComponent implements OnInit {
  private mymap: L.Map;
  private _track: Track;
  private _marker: L.Marker;
  private currentTrackPolyline: L.Polyline;
  private selectControl: L.Control.LineStringSelect;
  private segments: GeoJSON[];
  constructor(private trackService: TrackService,
              private segmentService: SegmentService,
              private route: ActivatedRoute) { }

  @Input()
  set marker(latLng: LatLng) {
    if(this.mymap == undefined) {
      return;
    }
    if(this._marker) {
      this._marker.setLatLng([latLng.lat, latLng.lng]);
    } else {
      this._marker = L.marker([latLng.lat, latLng.lng]).addTo(this.mymap);
    }
  }

  @Input()
  set track(track: Track) {
    this._track = track;
    if(track) {
      this.getAndDrawTrack();
    }
  }

  get track(): Track {
    return this._track;
  }

  getSelection(): L.LatLng[] {
    if(!this.selectControl) {
      return [];
    }
    return this.selectControl.getSelection();
  }

  @Input()
  set selectionMode(selectionMode: boolean) {
    if(selectionMode) {
      if(!this.track) return; // now track available
      if(this.selectControl) return; // already enabled
      this.selectControl = new L.Control.LineStringSelect({startMarkerClass: "select-marker select-start-marker"})
      this.mymap.addControl(this.selectControl);
      this.selectControl.enable({layer: this.currentTrackPolyline})
    } else {
      if(!this.selectControl) return; // already disabled
      this.mymap.removeControl(this.selectControl)
      this.selectControl = undefined;
    }
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      if(params.get('trackId')) {
        this.trackService.getTrack(Number(params.get('trackId'))).subscribe(track => {this.track = track})
      }
    });
  }

  private getAndDrawTrack() {
    let currentTrack = this._track;
    this.trackService.getTrackPoints(currentTrack.id).subscribe(data => {
      if(this.mymap == undefined) {
        this.initMap(currentTrack.boundaries);
      } else {
        this.mymap.flyToBounds(currentTrack.boundaries)
      }
      this.loadSegments(currentTrack.boundaries);
      this.drawTrack(data, 'blue');
    })
  }

  private initMap(boundaries: LatLngBounds) {
    this.mymap = L.map('mapid');
    if(boundaries) {
      this.mymap.fitBounds(boundaries);
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
    const latLng = (data).map(this.mapToLatLng);
    this.currentTrackPolyline = L.polyline(latLng, {color: color}).addTo(this.mymap);
    this.currentTrackPolyline.bringToBack()
  }

  private mapToLatLng(row : TrackPoint) : L.LatLng {
    return L.latLng(row.latitude, row.longitude)
  }

  private loadSegments(boundaries: LatLngBounds) {
    this.removeOldSegments();
    this.segmentService.getSegmentsInBoundingBox(
      {latitude: boundaries[0][0], longitude: boundaries[0][1]},
      {latitude: boundaries[1][0], longitude: boundaries[1][1]}
    ).subscribe(data => {
      this.segments = data.map( d => {
        return L.geoJSON(d.geoJson,{
          style: function (feature) {
            return {color: 'red'};
          }
        })});
      for(let s of this.segments) {
        s.addTo(this.mymap).bringToFront();
      }
    });
  }

  private removeOldSegments() {
    if(this.segments) {
      for(let s of this.segments) {
        s.removeFrom(this.mymap);
      }
    }
  }
}
