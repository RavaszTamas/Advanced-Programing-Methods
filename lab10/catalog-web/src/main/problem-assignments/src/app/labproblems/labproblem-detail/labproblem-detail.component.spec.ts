import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LabproblemDetailComponent} from './labproblem-detail.component';

describe('LabproblemDetailComponent', () => {
  let component: LabproblemDetailComponent;
  let fixture: ComponentFixture<LabproblemDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LabproblemDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabproblemDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
