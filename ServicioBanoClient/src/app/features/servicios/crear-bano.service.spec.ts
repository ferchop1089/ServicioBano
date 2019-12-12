import { TestBed } from '@angular/core/testing';

import { CrearBanoService } from './crear-bano.service';

describe('CrearBanoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CrearBanoService = TestBed.get(CrearBanoService);
    expect(service).toBeTruthy();
  });
});
