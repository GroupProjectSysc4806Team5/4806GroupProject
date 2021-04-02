import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookstoreDetailComponent } from './bookstore-detail.component';

describe('Component Tests', () => {
  describe('Bookstore Management Detail Component', () => {
    let comp: BookstoreDetailComponent;
    let fixture: ComponentFixture<BookstoreDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BookstoreDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ bookstore: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BookstoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookstoreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bookstore on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookstore).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
