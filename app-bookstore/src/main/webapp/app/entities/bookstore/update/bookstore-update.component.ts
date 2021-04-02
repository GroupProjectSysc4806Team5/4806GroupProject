import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBookstore, Bookstore } from '../bookstore.model';
import { BookstoreService } from '../service/bookstore.service';

@Component({
  selector: 'jhi-bookstore-update',
  templateUrl: './bookstore-update.component.html',
})
export class BookstoreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected bookstoreService: BookstoreService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookstore }) => {
      this.updateForm(bookstore);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bookstore = this.createFromForm();
    if (bookstore.id !== undefined) {
      this.subscribeToSaveResponse(this.bookstoreService.update(bookstore));
    } else {
      this.subscribeToSaveResponse(this.bookstoreService.create(bookstore));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookstore>>): void {
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

  protected updateForm(bookstore: IBookstore): void {
    this.editForm.patchValue({
      id: bookstore.id,
      name: bookstore.name,
    });
  }

  protected createFromForm(): IBookstore {
    return {
      ...new Bookstore(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
