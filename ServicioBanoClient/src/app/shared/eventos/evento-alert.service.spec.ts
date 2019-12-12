import { TestBed } from '@angular/core/testing';

import { EventoAlertService } from './evento-alert.service';

describe('EventoAlertService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventoAlertService = TestBed.get(EventoAlertService);
    expect(service).toBeTruthy();
  });
});
