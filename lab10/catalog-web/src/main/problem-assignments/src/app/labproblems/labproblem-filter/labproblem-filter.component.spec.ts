import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LabproblemFilterComponent} from './labproblem-filter.component';

describe('LabproblemFilterComponent', () => {
  let component: LabproblemFilterComponent;
  let fixture: ComponentFixture<LabproblemFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LabproblemFilterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabproblemFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
