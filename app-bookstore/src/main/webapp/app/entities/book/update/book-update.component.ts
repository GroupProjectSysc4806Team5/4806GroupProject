import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBook, Book } from '../book.model';
import { BookService } from '../service/book.service';
import { IBookstore } from 'app/entities/bookstore/bookstore.model';
import { BookstoreService } from 'app/entities/bookstore/service/bookstore.service';

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html',
})
export class BookUpdateComponent implements OnInit {
  isSaving = false;

  bookstoresSharedCollection: IBookstore[] = [];

  editForm = this.fb.group({
    id: [],
    author: [],
    available: [],
    bookName: [],
    description: [],
    isbn: [],
    picture: [],
    publisher: [],
    bookstore: [],
  });

  constructor(
    protected bookService: BookService,
    protected bookstoreService: BookstoreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  trackBookstoreById(index: number, item: IBookstore): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>): void {
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

  protected updateForm(book: IBook): void {
    this.editForm.patchValue({
      id: book.id,
      author: book.author,
      available: book.available,
      bookName: book.bookName,
      description: book.description,
      isbn: book.isbn,
      picture: book.picture,
      publisher: book.publisher,
      bookstore: book.bookstore,
    });

    this.bookstoresSharedCollection = this.bookstoreService.addBookstoreToCollectionIfMissing(
      this.bookstoresSharedCollection,
      book.bookstore
    );
  }

  protected loadRelationshipsOptions(): void {
    this.bookstoreService
      .query()
      .pipe(map((res: HttpResponse<IBookstore[]>) => res.body ?? []))
      .pipe(
        map((bookstores: IBookstore[]) =>
          this.bookstoreService.addBookstoreToCollectionIfMissing(bookstores, this.editForm.get('bookstore')!.value)
        )
      )
      .subscribe((bookstores: IBookstore[]) => (this.bookstoresSharedCollection = bookstores));
  }

  protected createFromForm(): IBook {
    return {
      ...new Book(),
      id: this.editForm.get(['id'])!.value,
      author: this.editForm.get(['author'])!.value,
      available: this.editForm.get(['available'])!.value,
      bookName: this.editForm.get(['bookName'])!.value,
      description: this.editForm.get(['description'])!.value,
      isbn: this.editForm.get(['isbn'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      publisher: this.editForm.get(['publisher'])!.value,
      bookstore: this.editForm.get(['bookstore'])!.value,
    };
  }
}
