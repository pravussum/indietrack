import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackviewComponent } from './trackview.component';

describe('TrackviewComponent', () => {
  let component: TrackviewComponent;
  let fixture: ComponentFixture<TrackviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
