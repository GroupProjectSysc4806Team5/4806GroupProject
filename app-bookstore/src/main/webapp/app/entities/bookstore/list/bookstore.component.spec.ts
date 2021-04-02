import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BookstoreService } from '../service/bookstore.service';

import { BookstoreComponent } from './bookstore.component';

describe('Component Tests', () => {
  describe('Bookstore Management Component', () => {
    let comp: BookstoreComponent;
    let fixture: ComponentFixture<BookstoreComponent>;
    let service: BookstoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BookstoreComponent],
      })
        .overrideTemplate(BookstoreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookstoreComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(BookstoreService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bookstores?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
