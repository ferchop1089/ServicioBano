import { TestBed } from '@angular/core/testing';

import { CompartirIdBanoService } from './compartir-id-bano.service';

describe('CompartirIdBanoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CompartirIdBanoService = TestBed.get(CompartirIdBanoService);
    expect(service).toBeTruthy();
  });
});
