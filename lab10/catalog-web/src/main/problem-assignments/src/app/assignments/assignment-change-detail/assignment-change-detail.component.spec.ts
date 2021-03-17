import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AssignmentChangeDetailComponent} from './assignment-change-detail.component';

describe('AssignmentChangeDetailComponent', () => {
  let component: AssignmentChangeDetailComponent;
  let fixture: ComponentFixture<AssignmentChangeDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AssignmentChangeDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentChangeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
