import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LabproblemsListPaginatedComponent} from './labproblems-list-paginated.component';

describe('LabproblemsListPaginatedComponent', () => {
  let component: LabproblemsListPaginatedComponent;
  let fixture: ComponentFixture<LabproblemsListPaginatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LabproblemsListPaginatedComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabproblemsListPaginatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
