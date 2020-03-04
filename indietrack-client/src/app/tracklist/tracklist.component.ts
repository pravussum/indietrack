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
  tracks: number[];
  selectedTrackId = 5900;

  ngOnInit() {
    this.http.get<number[]>("http://localhost:8099/track").subscribe(
      trackIds => {
        this.tracks = trackIds;
      });
  }

  onSelect(track: number) {
    console.log(track);
    this.selectedTrackId = track;
  }
}
