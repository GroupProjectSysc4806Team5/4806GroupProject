import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookstore } from '../bookstore.model';
import { BookstoreService } from '../service/bookstore.service';

@Component({
  templateUrl: './bookstore-delete-dialog.component.html',
})
export class BookstoreDeleteDialogComponent {
  bookstore?: IBookstore;

  constructor(protected bookstoreService: BookstoreService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookstoreService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
