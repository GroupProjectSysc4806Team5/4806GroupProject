import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookstore } from '../bookstore.model';
import { BookstoreService } from '../service/bookstore.service';
import { BookstoreDeleteDialogComponent } from '../delete/bookstore-delete-dialog.component';

@Component({
  selector: 'jhi-bookstore',
  templateUrl: './bookstore.component.html',
})
export class BookstoreComponent implements OnInit {
  bookstores?: IBookstore[];
  isLoading = false;

  constructor(protected bookstoreService: BookstoreService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.bookstoreService.query().subscribe(
      (res: HttpResponse<IBookstore[]>) => {
        this.isLoading = false;
        this.bookstores = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IBookstore): number {
    return item.id!;
  }

  delete(bookstore: IBookstore): void {
    const modalRef = this.modalService.open(BookstoreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bookstore = bookstore;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
