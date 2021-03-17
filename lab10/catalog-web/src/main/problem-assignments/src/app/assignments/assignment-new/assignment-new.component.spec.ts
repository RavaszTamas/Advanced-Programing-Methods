import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AssignmentNewComponent} from './assignment-new.component';

describe('AssignmentNewComponent', () => {
  let component: AssignmentNewComponent;
  let fixture: ComponentFixture<AssignmentNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AssignmentNewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
