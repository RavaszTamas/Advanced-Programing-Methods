import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AssignmentListPaginatedComponent} from './assignment-list-paginated.component';

describe('AssignmentListPaginatedComponent', () => {
  let component: AssignmentListPaginatedComponent;
  let fixture: ComponentFixture<AssignmentListPaginatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AssignmentListPaginatedComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentListPaginatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
