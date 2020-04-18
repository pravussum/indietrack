import {Component, OnInit, ViewChild} from '@angular/core';
import {TrackService} from "../track/track.service";
import {Track} from "../dto/Track";
import {TrackPoint} from "../dto/TrackPoint";
import {LatLng} from "leaflet";
import {TrackviewComponent} from "../trackview/trackview.component";

@Component({
  selector: 'app-tracklist',
  templateUrl: './tracklist.component.html',
  styleUrls: ['./tracklist.component.css']
})
export class TracklistComponent implements OnInit {

  @ViewChild(TrackviewComponent) trackView;

  constructor(private trackService: TrackService) { }

  trackPosToMark: LatLng;

  tracks: Track[];
  selectedTrack: Track;
  selectedTrackPoint: TrackPoint;
  segCreationMode: boolean;

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

  getActivityType(track: Track): string {
    let avgSpeed = this.getAvgInKmh(track);
    if(avgSpeed < 7) {
      return "walk"
    } else if(avgSpeed < 17) {
      return "run"
    } else if(avgSpeed < 45) {
      return "bike"
    } else return "moto";
  }

  getActivityIcon(activityType: string) {
    switch (activityType) {
      case "walk": return "walk-outline";
      case "run": return "";
      case "bike": return "bicycle-outline";
      case "moto": return "car-outline"

    }
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

  onCreateSegment() {
    this.segCreationMode = true;
  }

  saveSegment() {
    console.log(JSON.stringify(this.trackView.getSelection()));
  }
}
