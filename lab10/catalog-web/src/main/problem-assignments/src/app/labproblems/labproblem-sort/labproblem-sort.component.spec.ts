import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LabproblemSortComponent} from './labproblem-sort.component';

describe('LabproblemSortComponent', () => {
  let component: LabproblemSortComponent;
  let fixture: ComponentFixture<LabproblemSortComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LabproblemSortComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabproblemSortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
