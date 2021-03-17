import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LabproblemsComponent} from './labproblems.component';

describe('LabproblemsComponent', () => {
  let component: LabproblemsComponent;
  let fixture: ComponentFixture<LabproblemsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LabproblemsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabproblemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
