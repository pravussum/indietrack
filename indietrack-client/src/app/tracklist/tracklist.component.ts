import {Component, OnInit} from '@angular/core';
import {TrackService} from "../track/track.service";
import {Track} from "../dto/Track";

@Component({
  selector: 'app-tracklist',
  templateUrl: './tracklist.component.html',
  styleUrls: ['./tracklist.component.css']
})
export class TracklistComponent implements OnInit {
  constructor(private trackService: TrackService) { }
  tracks: Track[];
  selectedTrack: Track;

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
}
