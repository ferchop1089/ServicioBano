import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EliminarBanoComponent } from './eliminar-bano.component';

describe('EliminarBanoComponent', () => {
  let component: EliminarBanoComponent;
  let fixture: ComponentFixture<EliminarBanoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EliminarBanoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EliminarBanoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
