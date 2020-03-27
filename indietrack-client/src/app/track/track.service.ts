import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TrackService {

  constructor(private http: HttpClient) { }

  public getTracks(): Observable<Track[]> {
    return this.http.get<Track[]>("./assets/tracklist.json");
    // ("http://localhost:8099/track")
  }

  public getTrackPoints(trackId: number): Observable<TrackPoint[]> {
    return this.http.get<TrackPoint[]>("./assets/track.json");
  }
}
