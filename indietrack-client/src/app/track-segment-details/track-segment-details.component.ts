import {Component, Input, OnInit} from '@angular/core';
import {SegmentService} from "../segment/segment.service";
import {Attempt} from "../dto/Attempt";
import {Track} from "../dto/Track";

@Component({
  selector: 'app-track-segment-details',
  templateUrl: './track-segment-details.component.html',
  styleUrls: ['./track-segment-details.component.css']
})
export class TrackSegmentDetailsComponent implements OnInit {

  constructor(private segmentService: SegmentService) { }

  private _trackId;
  public attempts: Attempt[];

  @Input()
  set trackId(trackId: number) {
    this._trackId = trackId;
    if(trackId) {
      this.loadSegmentDetailsForTrack(trackId);
    }
  }

  get trackId(): number {
    return this._trackId;
  }

  ngOnInit(): void {

  }

  private loadSegmentDetailsForTrack(trackId: number) {
    this.segmentService.getSegments(trackId).subscribe(data => {
      this.attempts = data;
    }, err => console.log(err));
  }

  getAvgSpeed(attempt: Attempt) {
    if(!attempt) return undefined;
    return (attempt.length / 1000) / (attempt.durationInSec / 60 / 60);
  }
}
