import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarBanosComponent } from './listar-banos.component';

describe('ListarBanosComponent', () => {
  let component: ListarBanosComponent;
  let fixture: ComponentFixture<ListarBanosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListarBanosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListarBanosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
