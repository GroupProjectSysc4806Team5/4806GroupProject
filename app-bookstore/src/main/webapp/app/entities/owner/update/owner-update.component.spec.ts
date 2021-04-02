jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OwnerService } from '../service/owner.service';
import { IOwner, Owner } from '../owner.model';
import { IBookstore } from 'app/entities/bookstore/bookstore.model';
import { BookstoreService } from 'app/entities/bookstore/service/bookstore.service';

import { OwnerUpdateComponent } from './owner-update.component';

describe('Component Tests', () => {
  describe('Owner Management Update Component', () => {
    let comp: OwnerUpdateComponent;
    let fixture: ComponentFixture<OwnerUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ownerService: OwnerService;
    let bookstoreService: BookstoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OwnerUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OwnerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OwnerUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ownerService = TestBed.inject(OwnerService);
      bookstoreService = TestBed.inject(BookstoreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Bookstore query and add missing value', () => {
        const owner: IOwner = { id: 456 };
        const bookstore: IBookstore = { id: 26257 };
        owner.bookstore = bookstore;

        const bookstoreCollection: IBookstore[] = [{ id: 86962 }];
        spyOn(bookstoreService, 'query').and.returnValue(of(new HttpResponse({ body: bookstoreCollection })));
        const additionalBookstores = [bookstore];
        const expectedCollection: IBookstore[] = [...additionalBookstores, ...bookstoreCollection];
        spyOn(bookstoreService, 'addBookstoreToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ owner });
        comp.ngOnInit();

        expect(bookstoreService.query).toHaveBeenCalled();
        expect(bookstoreService.addBookstoreToCollectionIfMissing).toHaveBeenCalledWith(bookstoreCollection, ...additionalBookstores);
        expect(comp.bookstoresSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const owner: IOwner = { id: 456 };
        const bookstore: IBookstore = { id: 57830 };
        owner.bookstore = bookstore;

        activatedRoute.data = of({ owner });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(owner));
        expect(comp.bookstoresSharedCollection).toContain(bookstore);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const owner = { id: 123 };
        spyOn(ownerService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ owner });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: owner }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ownerService.update).toHaveBeenCalledWith(owner);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const owner = new Owner();
        spyOn(ownerService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ owner });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: owner }));
        saveSubject.complete();

        // THEN
        expect(ownerService.create).toHaveBeenCalledWith(owner);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const owner = { id: 123 };
        spyOn(ownerService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ owner });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ownerService.update).toHaveBeenCalledWith(owner);
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
