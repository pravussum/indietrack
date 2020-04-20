import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Segment} from "../dto/Segment";
import {Observable} from "rxjs";
import {Attempt} from "../dto/Attempt";
import {SegmentGeoJson} from "../dto/SegmentGeoJson";
import {LatitudeLongitude} from "../dto/LatitudeLongitude";

@Injectable({
  providedIn: 'root'
})
export class SegmentService {

  constructor(private client: HttpClient) { }

  public createSegment(segment: Segment): Observable<number> {
    return this.client.post<number>("/api/segment", segment);
  }

  public getSegments(trackId: number): Observable<Attempt[]> {
    return this.client.get<Attempt[]>("/api/segment/attempts",{ params: new HttpParams().append("trackId", trackId.toString())})
  }

  public getSegmentsInBoundingBox(lowerLeft: LatitudeLongitude, upperRight: LatitudeLongitude): Observable<SegmentGeoJson[]> {
    return this.client.post<SegmentGeoJson[]>("/api/segment/geojson",
      {"boundingBoxLowerLeft": lowerLeft, "boundingBoxUpperRight": upperRight}
    );
  }
}
