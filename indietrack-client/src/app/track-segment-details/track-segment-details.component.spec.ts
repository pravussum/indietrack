import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackSegmentDetailsComponent } from './track-segment-details.component';

describe('TrackSegmentDetailsComponent', () => {
  let component: TrackSegmentDetailsComponent;
  let fixture: ComponentFixture<TrackSegmentDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackSegmentDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackSegmentDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
