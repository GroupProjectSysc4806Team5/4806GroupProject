import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookstoreViewComponent } from './bookstore-view.component';

describe('BookstoreViewComponent', () => {
  let component: BookstoreViewComponent;
  let fixture: ComponentFixture<BookstoreViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookstoreViewComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookstoreViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
