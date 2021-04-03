import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBookstore, Bookstore } from '../bookstore.model';
import { BookstoreService } from '../service/bookstore.service';
import { IOwner } from 'app/entities/owner/owner.model';
import { OwnerService } from 'app/entities/owner/service/owner.service';

@Component({
  selector: 'jhi-bookstore-update',
  templateUrl: './bookstore-update.component.html',
})
export class BookstoreUpdateComponent implements OnInit {
  isSaving = false;

  ownersSharedCollection: IOwner[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    owner: [],
  });

  constructor(
    protected bookstoreService: BookstoreService,
    protected ownerService: OwnerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookstore }) => {
      this.updateForm(bookstore);

      this.loadRelationshipsOptions();
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

  trackOwnerById(index: number, item: IOwner): number {
    return item.id!;
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
      owner: bookstore.owner,
    });

    this.ownersSharedCollection = this.ownerService.addOwnerToCollectionIfMissing(this.ownersSharedCollection, bookstore.owner);
  }

  protected loadRelationshipsOptions(): void {
    this.ownerService
      .query()
      .pipe(map((res: HttpResponse<IOwner[]>) => res.body ?? []))
      .pipe(map((owners: IOwner[]) => this.ownerService.addOwnerToCollectionIfMissing(owners, this.editForm.get('owner')!.value)))
      .subscribe((owners: IOwner[]) => (this.ownersSharedCollection = owners));
  }

  protected createFromForm(): IBookstore {
    return {
      ...new Bookstore(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      owner: this.editForm.get(['owner'])!.value,
    };
  }
}
