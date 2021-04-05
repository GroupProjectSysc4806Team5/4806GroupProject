import { Component, OnInit } from '@angular/core';
import { Account } from 'app/core/auth/account.model';
import { Subscription } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';
import { ActivatedRoute } from '@angular/router';
import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';
import { HttpResponse } from '@angular/common/http';
import { BookstoreService } from 'app/entities/bookstore/service/bookstore.service';
import { IBookstore } from 'app/entities/bookstore/bookstore.model';

@Component({
  selector: 'jhi-bookstore-view',
  templateUrl: './bookstore-view.component.html',
  styleUrls: ['./bookstore-view.component.scss'],
})
export class BookstoreViewComponent implements OnInit {
  account: Account | null = null;
  authSubscription?: Subscription;
  isLoading?: boolean;
  bookstoreId = 1;
  bookstore?: IBookstore | null;
  bookstoreName: string | null = 'Bookstore';
  bookList?: IBook[];

  constructor(
    private accountService: AccountService,
    private bookService: BookService,
    private bookstoreService: BookstoreService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.bookstoreId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.loadAll();
  }

  loadAll(): void {
    this.isLoading = true;
    this.bookService.findByBookstore(this.bookstoreId).subscribe(
      (res: HttpResponse<IBook[]>) => {
        this.isLoading = false;
        this.bookList = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
    this.bookstoreService.find(this.bookstoreId).subscribe(
      (res: HttpResponse<IBookstore>) => {
        this.isLoading = false;
        this.bookstore = res.body ?? null;

        this.bookList = this.bookstore?.books ? this.bookstore.books : this.bookList;
        this.bookstoreName = this.bookstore?.name ? this.bookstore.name : this.bookstoreName;
      },
      () => {
        this.isLoading = false;
      }
    );
  }
}
