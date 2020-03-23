import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {assertNumber} from "@angular/core/src/render3/assert";

@Component({
  selector: 'app-tracklist',
  templateUrl: './tracklist.component.html',
  styleUrls: ['./tracklist.component.css']
})
export class TracklistComponent implements OnInit {

  constructor(private http:HttpClient) { }
  tracks: Track[];
  selectedTrackId;

  ngOnInit() {
    this.http.get<Track[]>("http://localhost:8099/track").subscribe(
      trackInfo => {
        this.tracks = trackInfo;
      });
  }

  onSelect(track: Track) {
    console.log(track);
    this.selectedTrackId = track.id;
  }
}
