import { TestBed } from '@angular/core/testing';

import { ConsultarBanosService } from './consultar-banos.service';

describe('ConsultarBanosService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConsultarBanosService = TestBed.get(ConsultarBanosService);
    expect(service).toBeTruthy();
  });
});
