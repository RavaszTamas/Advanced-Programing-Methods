import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {StudentListSortedComponent} from './student-list-sorted.component';

describe('StudentListSortedComponent', () => {
  let component: StudentListSortedComponent;
  let fixture: ComponentFixture<StudentListSortedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StudentListSortedComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentListSortedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
