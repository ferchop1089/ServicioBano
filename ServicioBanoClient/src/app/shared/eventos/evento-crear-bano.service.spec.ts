import { TestBed } from '@angular/core/testing';

import { EventoCrearBanoService } from './evento-crear-bano.service';

describe('EventoCrearBanoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventoCrearBanoService = TestBed.get(EventoCrearBanoService);
    expect(service).toBeTruthy();
  });
});
