import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearBanoComponent } from './crear-bano.component';

describe('CrearBanoComponent', () => {
  let component: CrearBanoComponent;
  let fixture: ComponentFixture<CrearBanoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CrearBanoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CrearBanoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
