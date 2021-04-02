import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICart, Cart } from '../cart.model';
import { CartService } from '../service/cart.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';

@Component({
  selector: 'jhi-cart-update',
  templateUrl: './cart-update.component.html',
})
export class CartUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];
  booksSharedCollection: IBook[] = [];

  editForm = this.fb.group({
    id: [],
    user: [],
    books: [],
  });

  constructor(
    protected cartService: CartService,
    protected userService: UserService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cart }) => {
      this.updateForm(cart);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cart = this.createFromForm();
    if (cart.id !== undefined) {
      this.subscribeToSaveResponse(this.cartService.update(cart));
    } else {
      this.subscribeToSaveResponse(this.cartService.create(cart));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackBookById(index: number, item: IBook): number {
    return item.id!;
  }

  getSelectedBook(option: IBook, selectedVals?: IBook[]): IBook {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>): void {
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

  protected updateForm(cart: ICart): void {
    this.editForm.patchValue({
      id: cart.id,
      user: cart.user,
      books: cart.books,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, cart.user);
    this.booksSharedCollection = this.bookService.addBookToCollectionIfMissing(this.booksSharedCollection, ...(cart.books ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.bookService
      .query()
      .pipe(map((res: HttpResponse<IBook[]>) => res.body ?? []))
      .pipe(map((books: IBook[]) => this.bookService.addBookToCollectionIfMissing(books, ...(this.editForm.get('books')!.value ?? []))))
      .subscribe((books: IBook[]) => (this.booksSharedCollection = books));
  }

  protected createFromForm(): ICart {
    return {
      ...new Cart(),
      id: this.editForm.get(['id'])!.value,
      user: this.editForm.get(['user'])!.value,
      books: this.editForm.get(['books'])!.value,
    };
  }
}
