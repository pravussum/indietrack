import {Component, OnInit} from '@angular/core';
import {TrackService} from "../track/track.service";
import {Track} from "../dto/Track";
import {TrackPoint} from "../dto/TrackPoint";
import {LatLng} from "leaflet";

@Component({
  selector: 'app-tracklist',
  templateUrl: './tracklist.component.html',
  styleUrls: ['./tracklist.component.css']
})
export class TracklistComponent implements OnInit {

  constructor(private trackService: TrackService) { }

  trackPosToMark: LatLng;

  tracks: Track[];
  selectedTrack: Track;
  selectedTrackPoint: TrackPoint;

  ngOnInit() {
    this.trackService.getTracks().subscribe(
      trackInfo => {
        this.tracks = trackInfo;
      });
  }

  onSelect(track: Track) {
    this.selectedTrack = track;
  }

  getDurationInMin(track: Track): number {
    if(track) {
      return (new Date(track.endTime).getTime() - new Date(track.startTime).getTime()) / 60000;
    } else return undefined;
  }

  getAvgInKmh(track: Track): number {
    if(track) {
      return (track.distance / 1000) / (this.getDurationInMin(track) / 60);
    } else return undefined;
  }

  onTrackPointSelectedInHeightGraph(trackPoint: TrackPoint) {
    this.selectedTrackPoint = trackPoint;
    this.trackPosToMark = new LatLng(trackPoint.latitude, trackPoint.longitude, trackPoint.elevation);
  }
}
