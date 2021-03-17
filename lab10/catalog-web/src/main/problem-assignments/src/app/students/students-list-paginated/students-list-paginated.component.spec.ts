import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {StudentsListPaginatedComponent} from './students-list-paginated.component';

describe('StudentsListPaginatedComponent', () => {
  let component: StudentsListPaginatedComponent;
  let fixture: ComponentFixture<StudentsListPaginatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [StudentsListPaginatedComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StudentsListPaginatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
