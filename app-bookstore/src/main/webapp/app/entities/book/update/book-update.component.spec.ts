jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BookService } from '../service/book.service';
import { IBook, Book } from '../book.model';
import { IBookstore } from 'app/entities/bookstore/bookstore.model';
import { BookstoreService } from 'app/entities/bookstore/service/bookstore.service';

import { BookUpdateComponent } from './book-update.component';

describe('Component Tests', () => {
  describe('Book Management Update Component', () => {
    let comp: BookUpdateComponent;
    let fixture: ComponentFixture<BookUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let bookService: BookService;
    let bookstoreService: BookstoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BookUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BookUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      bookService = TestBed.inject(BookService);
      bookstoreService = TestBed.inject(BookstoreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Bookstore query and add missing value', () => {
        const book: IBook = { id: 456 };
        const bookstore: IBookstore = { id: 52662 };
        book.bookstore = bookstore;

        const bookstoreCollection: IBookstore[] = [{ id: 56616 }];
        spyOn(bookstoreService, 'query').and.returnValue(of(new HttpResponse({ body: bookstoreCollection })));
        const additionalBookstores = [bookstore];
        const expectedCollection: IBookstore[] = [...additionalBookstores, ...bookstoreCollection];
        spyOn(bookstoreService, 'addBookstoreToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ book });
        comp.ngOnInit();

        expect(bookstoreService.query).toHaveBeenCalled();
        expect(bookstoreService.addBookstoreToCollectionIfMissing).toHaveBeenCalledWith(bookstoreCollection, ...additionalBookstores);
        expect(comp.bookstoresSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const book: IBook = { id: 456 };
        const bookstore: IBookstore = { id: 89620 };
        book.bookstore = bookstore;

        activatedRoute.data = of({ book });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(book));
        expect(comp.bookstoresSharedCollection).toContain(bookstore);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const book = { id: 123 };
        spyOn(bookService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ book });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: book }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(bookService.update).toHaveBeenCalledWith(book);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const book = new Book();
        spyOn(bookService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ book });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: book }));
        saveSubject.complete();

        // THEN
        expect(bookService.create).toHaveBeenCalledWith(book);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const book = { id: 123 };
        spyOn(bookService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ book });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(bookService.update).toHaveBeenCalledWith(book);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackBookstoreById', () => {
        it('Should return tracked Bookstore primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackBookstoreById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
