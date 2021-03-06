import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {StudentsFilterComponent} from './students-filter.component';

describe('StudentsFilterComponent', () => {
  let component: StudentsFilterComponent;
  let fixture: ComponentFixture<StudentsFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StudentsFilterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentsFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
