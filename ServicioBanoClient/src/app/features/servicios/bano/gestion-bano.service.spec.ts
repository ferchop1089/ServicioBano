import { TestBed } from '@angular/core/testing';

import { GestionBanoService } from './gestion-bano.service';

describe('GestionBanoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GestionBanoService = TestBed.get(GestionBanoService);
    expect(service).toBeTruthy();
  });
});
