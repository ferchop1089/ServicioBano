import { TestBed } from '@angular/core/testing';

import { EventoCobrarCuentaService } from './evento-cobrar-cuenta.service';

describe('EventoCobrarCuentaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventoCobrarCuentaService = TestBed.get(EventoCobrarCuentaService);
    expect(service).toBeTruthy();
  });
});
