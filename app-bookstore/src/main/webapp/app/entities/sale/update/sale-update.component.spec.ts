jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SaleService } from '../service/sale.service';
import { ISale, Sale } from '../sale.model';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';

import { SaleUpdateComponent } from './sale-update.component';

describe('Component Tests', () => {
  describe('Sale Management Update Component', () => {
    let comp: SaleUpdateComponent;
    let fixture: ComponentFixture<SaleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let saleService: SaleService;
    let cartService: CartService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SaleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SaleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SaleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      saleService = TestBed.inject(SaleService);
      cartService = TestBed.inject(CartService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call cart query and add missing value', () => {
        const sale: ISale = { id: 456 };
        const cart: ICart = { id: 43537 };
        sale.cart = cart;

        const cartCollection: ICart[] = [{ id: 91474 }];
        spyOn(cartService, 'query').and.returnValue(of(new HttpResponse({ body: cartCollection })));
        const expectedCollection: ICart[] = [cart, ...cartCollection];
        spyOn(cartService, 'addCartToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sale });
        comp.ngOnInit();

        expect(cartService.query).toHaveBeenCalled();
        expect(cartService.addCartToCollectionIfMissing).toHaveBeenCalledWith(cartCollection, cart);
        expect(comp.cartsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const sale: ISale = { id: 456 };
        const cart: ICart = { id: 59236 };
        sale.cart = cart;

        activatedRoute.data = of({ sale });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(sale));
        expect(comp.cartsCollection).toContain(cart);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sale = { id: 123 };
        spyOn(saleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sale });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sale }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(saleService.update).toHaveBeenCalledWith(sale);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sale = new Sale();
        spyOn(saleService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sale });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sale }));
        saveSubject.complete();

        // THEN
        expect(saleService.create).toHaveBeenCalledWith(sale);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sale = { id: 123 };
        spyOn(saleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sale });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(saleService.update).toHaveBeenCalledWith(sale);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCartById', () => {
        it('Should return tracked Cart primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCartById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
