import {Component, OnInit} from '@angular/core';
import {TrackService} from "../track/track.service";

@Component({
  selector: 'app-tracklist',
  templateUrl: './tracklist.component.html',
  styleUrls: ['./tracklist.component.css']
})
export class TracklistComponent implements OnInit {

  constructor(private trackService: TrackService) { }
  tracks: Track[];
  selectedTrackId;

  ngOnInit() {
    this.trackService.getTracks().subscribe(
      trackInfo => {
        this.tracks = trackInfo;
      });
  }

  onSelect(track: Track) {
    console.log(track);
    this.selectedTrackId = track.id;
  }
}
