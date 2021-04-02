import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookstore } from '../bookstore.model';

@Component({
  selector: 'jhi-bookstore-detail',
  templateUrl: './bookstore-detail.component.html',
})
export class BookstoreDetailComponent implements OnInit {
  bookstore: IBookstore | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookstore }) => {
      this.bookstore = bookstore;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
