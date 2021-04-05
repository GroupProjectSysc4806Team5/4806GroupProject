import { Component, OnInit } from '@angular/core';
import { Account } from 'app/core/auth/account.model';
import { Subscription } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';
import { ActivatedRoute } from '@angular/router';
import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';
import { HttpResponse } from '@angular/common/http';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';

@Component({
  selector: 'jhi-book-view',
  templateUrl: './book-view.component.html',
  styleUrls: ['./book-view.component.scss'],
})
export class BookViewComponent implements OnInit {
  account: Account | null = null;
  authSubscription?: Subscription;
  bookId = 1;
  book: IBook | null = null;
  isLoading = true;
  cartId = 1;
  customer: ICustomer | null = null;

  constructor(
    private accountService: AccountService,
    private bookService: BookService,
    private customerService: CustomerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.bookId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.bookService.find(this.bookId).subscribe(
      (res: HttpResponse<IBook>) => {
        this.isLoading = false;
        this.book = res.body ?? null;
      },
      () => {
        this.isLoading = false;
      }
    );
    if (this.account) {
      this.customerService.findByLogin(this.account.login).subscribe(
        (res: HttpResponse<ICustomer>) => {
          this.isLoading = false;
          this.customer = res.body ?? null;
        },
        () => {
          this.isLoading = false;
        }
      );
    }
  }

  previousState(): void {
    window.history.back();
  }
}
