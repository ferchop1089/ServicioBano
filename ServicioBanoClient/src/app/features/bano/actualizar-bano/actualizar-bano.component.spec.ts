import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualizarBanoComponent } from './actualizar-bano.component';

describe('ActualizarBanoComponent', () => {
  let component: ActualizarBanoComponent;
  let fixture: ComponentFixture<ActualizarBanoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActualizarBanoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActualizarBanoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
