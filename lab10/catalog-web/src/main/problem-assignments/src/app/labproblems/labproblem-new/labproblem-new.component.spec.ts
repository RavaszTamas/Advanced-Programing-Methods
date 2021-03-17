import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LabproblemNewComponent} from './labproblem-new.component';

describe('LabproblemNewComponent', () => {
  let component: LabproblemNewComponent;
  let fixture: ComponentFixture<LabproblemNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LabproblemNewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabproblemNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
