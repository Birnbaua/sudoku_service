import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PicUploaderComponent } from './pic-uploader.component';

describe('PicUploaderComponent', () => {
  let component: PicUploaderComponent;
  let fixture: ComponentFixture<PicUploaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PicUploaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PicUploaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
