import { TestBed } from '@angular/core/testing';

import { ManejadorErroresService } from './manejador-errores.service';

describe('ManejadorErroresService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ManejadorErroresService = TestBed.get(ManejadorErroresService);
    expect(service).toBeTruthy();
  });
});
