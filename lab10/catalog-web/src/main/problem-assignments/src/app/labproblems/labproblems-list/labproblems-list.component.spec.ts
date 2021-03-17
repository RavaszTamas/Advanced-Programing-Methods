import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LabproblemsListComponent} from './labproblems-list.component';

describe('LabproblemsListComponent', () => {
  let component: LabproblemsListComponent;
  let fixture: ComponentFixture<LabproblemsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LabproblemsListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabproblemsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
