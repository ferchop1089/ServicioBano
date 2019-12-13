import { TestBed } from '@angular/core/testing';

import { GestionCuentaService } from './gestion-cuenta.service';

describe('GestionCuentaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GestionCuentaService = TestBed.get(GestionCuentaService);
    expect(service).toBeTruthy();
  });
});
