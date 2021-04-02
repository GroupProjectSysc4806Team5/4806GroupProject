jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BookstoreService } from '../service/bookstore.service';
import { IBookstore, Bookstore } from '../bookstore.model';

import { BookstoreUpdateComponent } from './bookstore-update.component';

describe('Component Tests', () => {
  describe('Bookstore Management Update Component', () => {
    let comp: BookstoreUpdateComponent;
    let fixture: ComponentFixture<BookstoreUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let bookstoreService: BookstoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BookstoreUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BookstoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookstoreUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      bookstoreService = TestBed.inject(BookstoreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const bookstore: IBookstore = { id: 456 };

        activatedRoute.data = of({ bookstore });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(bookstore));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookstore = { id: 123 };
        spyOn(bookstoreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookstore });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bookstore }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(bookstoreService.update).toHaveBeenCalledWith(bookstore);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookstore = new Bookstore();
        spyOn(bookstoreService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookstore });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bookstore }));
        saveSubject.complete();

        // THEN
        expect(bookstoreService.create).toHaveBeenCalledWith(bookstore);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookstore = { id: 123 };
        spyOn(bookstoreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookstore });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(bookstoreService.update).toHaveBeenCalledWith(bookstore);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
