import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CobrarCuentaComponent } from './cobrar-cuenta.component';

describe('CobrarCuentaComponent', () => {
  let component: CobrarCuentaComponent;
  let fixture: ComponentFixture<CobrarCuentaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CobrarCuentaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CobrarCuentaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
