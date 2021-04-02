import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISale, Sale } from '../sale.model';
import { SaleService } from '../service/sale.service';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';

@Component({
  selector: 'jhi-sale-update',
  templateUrl: './sale-update.component.html',
})
export class SaleUpdateComponent implements OnInit {
  isSaving = false;

  cartsCollection: ICart[] = [];

  editForm = this.fb.group({
    id: [],
    cart: [],
  });

  constructor(
    protected saleService: SaleService,
    protected cartService: CartService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sale }) => {
      this.updateForm(sale);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sale = this.createFromForm();
    if (sale.id !== undefined) {
      this.subscribeToSaveResponse(this.saleService.update(sale));
    } else {
      this.subscribeToSaveResponse(this.saleService.create(sale));
    }
  }

  trackCartById(index: number, item: ICart): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISale>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(sale: ISale): void {
    this.editForm.patchValue({
      id: sale.id,
      cart: sale.cart,
    });

    this.cartsCollection = this.cartService.addCartToCollectionIfMissing(this.cartsCollection, sale.cart);
  }

  protected loadRelationshipsOptions(): void {
    this.cartService
      .query({ filter: 'sale-is-null' })
      .pipe(map((res: HttpResponse<ICart[]>) => res.body ?? []))
      .pipe(map((carts: ICart[]) => this.cartService.addCartToCollectionIfMissing(carts, this.editForm.get('cart')!.value)))
      .subscribe((carts: ICart[]) => (this.cartsCollection = carts));
  }

  protected createFromForm(): ISale {
    return {
      ...new Sale(),
      id: this.editForm.get(['id'])!.value,
      cart: this.editForm.get(['cart'])!.value,
    };
  }
}
