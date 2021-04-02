import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IOwner, Owner } from '../owner.model';
import { OwnerService } from '../service/owner.service';
import { IBookstore } from 'app/entities/bookstore/bookstore.model';
import { BookstoreService } from 'app/entities/bookstore/service/bookstore.service';

@Component({
  selector: 'jhi-owner-update',
  templateUrl: './owner-update.component.html',
})
export class OwnerUpdateComponent implements OnInit {
  isSaving = false;

  bookstoresSharedCollection: IBookstore[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    password: [],
    bookstore: [],
  });

  constructor(
    protected ownerService: OwnerService,
    protected bookstoreService: BookstoreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ owner }) => {
      this.updateForm(owner);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const owner = this.createFromForm();
    if (owner.id !== undefined) {
      this.subscribeToSaveResponse(this.ownerService.update(owner));
    } else {
      this.subscribeToSaveResponse(this.ownerService.create(owner));
    }
  }

  trackBookstoreById(index: number, item: IBookstore): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOwner>>): void {
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

  protected updateForm(owner: IOwner): void {
    this.editForm.patchValue({
      id: owner.id,
      name: owner.name,
      password: owner.password,
      bookstore: owner.bookstore,
    });

    this.bookstoresSharedCollection = this.bookstoreService.addBookstoreToCollectionIfMissing(
      this.bookstoresSharedCollection,
      owner.bookstore
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

  protected createFromForm(): IOwner {
    return {
      ...new Owner(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      password: this.editForm.get(['password'])!.value,
      bookstore: this.editForm.get(['bookstore'])!.value,
    };
  }
}
