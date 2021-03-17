import {TestBed} from '@angular/core/testing';

import {LabproblemsService} from './labproblems.service';

describe('LabproblemsService', () => {
  let service: LabproblemsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LabproblemsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
