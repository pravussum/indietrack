import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Track} from "../dto/Track";

@Injectable({
  providedIn: 'root'
})
export class TrackService {

  constructor(private http: HttpClient) { }

  public getTracks(): Observable<Track[]> {
    // return this.http.get<Track[]>("./assets/tracklist.json");
    return this.http.get<Track[]>("http://localhost:8099/track");
  }

  public getTrack(trackId: number): Observable<Track> {
    return this.http.get<Track>("http://localhost:8099/track/" + trackId);
  }

  public getTrackPoints(trackId: number): Observable<TrackPoint[]> {
    // return this.http.get<TrackPoint[]>("./assets/track.json");
    return this.http.get<TrackPoint[]>("http://localhost:8099/track/" + trackId + "/trackpoints");
  }
}
