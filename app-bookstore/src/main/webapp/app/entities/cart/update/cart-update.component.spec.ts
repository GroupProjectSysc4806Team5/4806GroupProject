jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CartService } from '../service/cart.service';
import { ICart, Cart } from '../cart.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';

import { CartUpdateComponent } from './cart-update.component';

describe('Component Tests', () => {
  describe('Cart Management Update Component', () => {
    let comp: CartUpdateComponent;
    let fixture: ComponentFixture<CartUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cartService: CartService;
    let userService: UserService;
    let bookService: BookService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CartUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CartUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CartUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cartService = TestBed.inject(CartService);
      userService = TestBed.inject(UserService);
      bookService = TestBed.inject(BookService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call User query and add missing value', () => {
        const cart: ICart = { id: 456 };
        const user: IUser = { id: 76131 };
        cart.user = user;

        const userCollection: IUser[] = [{ id: 28218 }];
        spyOn(userService, 'query').and.returnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        spyOn(userService, 'addUserToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ cart });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Book query and add missing value', () => {
        const cart: ICart = { id: 456 };
        const books: IBook[] = [{ id: 66986 }];
        cart.books = books;

        const bookCollection: IBook[] = [{ id: 666 }];
        spyOn(bookService, 'query').and.returnValue(of(new HttpResponse({ body: bookCollection })));
        const additionalBooks = [...books];
        const expectedCollection: IBook[] = [...additionalBooks, ...bookCollection];
        spyOn(bookService, 'addBookToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ cart });
        comp.ngOnInit();

        expect(bookService.query).toHaveBeenCalled();
        expect(bookService.addBookToCollectionIfMissing).toHaveBeenCalledWith(bookCollection, ...additionalBooks);
        expect(comp.booksSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const cart: ICart = { id: 456 };
        const user: IUser = { id: 6441 };
        cart.user = user;
        const books: IBook = { id: 80961 };
        cart.books = [books];

        activatedRoute.data = of({ cart });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cart));
        expect(comp.usersSharedCollection).toContain(user);
        expect(comp.booksSharedCollection).toContain(books);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cart = { id: 123 };
        spyOn(cartService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cart });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cart }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cartService.update).toHaveBeenCalledWith(cart);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cart = new Cart();
        spyOn(cartService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cart });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cart }));
        saveSubject.complete();

        // THEN
        expect(cartService.create).toHaveBeenCalledWith(cart);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cart = { id: 123 };
        spyOn(cartService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cart });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cartService.update).toHaveBeenCalledWith(cart);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackBookById', () => {
        it('Should return tracked Book primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackBookById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedBook', () => {
        it('Should return option if no Book is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedBook(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Book for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedBook(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Book is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedBook(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
