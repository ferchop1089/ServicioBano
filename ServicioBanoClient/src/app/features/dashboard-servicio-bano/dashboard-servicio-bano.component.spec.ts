import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardServicioBanoComponent } from './dashboard-servicio-bano.component';

describe('DashboardServicioBanoComponent', () => {
  let component: DashboardServicioBanoComponent;
  let fixture: ComponentFixture<DashboardServicioBanoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardServicioBanoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardServicioBanoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
