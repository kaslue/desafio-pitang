import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCarFormComponent } from './new-car-form.component';

describe('NewCarFormComponent', () => {
  let component: NewCarFormComponent;
  let fixture: ComponentFixture<NewCarFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewCarFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCarFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
