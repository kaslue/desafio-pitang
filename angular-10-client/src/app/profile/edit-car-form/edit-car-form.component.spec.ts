import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCarFormComponent } from './edit-car-form.component';

describe('EditCarFormComponent', () => {
  let component: EditCarFormComponent;
  let fixture: ComponentFixture<EditCarFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditCarFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCarFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
