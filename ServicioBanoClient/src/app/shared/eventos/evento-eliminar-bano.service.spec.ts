import { TestBed } from '@angular/core/testing';

import { EventoEliminarBanoService } from './evento-eliminar-bano.service';

describe('EventoEliminarBanoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventoEliminarBanoService = TestBed.get(EventoEliminarBanoService);
    expect(service).toBeTruthy();
  });
});
