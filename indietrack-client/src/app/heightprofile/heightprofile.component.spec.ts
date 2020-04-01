import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeightprofileComponent } from './heightprofile.component';

describe('HeightprofileComponent', () => {
  let component: HeightprofileComponent;
  let fixture: ComponentFixture<HeightprofileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeightprofileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeightprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
