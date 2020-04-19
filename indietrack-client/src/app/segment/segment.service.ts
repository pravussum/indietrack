import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Segment} from "../dto/Segment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SegmentService {

  constructor(private client: HttpClient) { }

  public createSegment(segment: Segment): Observable<number> {
    return this.client.post<number>("/api/segment", segment);
  }
}
