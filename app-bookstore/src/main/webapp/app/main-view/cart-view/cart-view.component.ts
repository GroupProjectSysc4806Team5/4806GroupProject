import { Component, OnInit } from '@angular/core';
import { Account } from 'app/core/auth/account.model';
import { Subscription } from 'rxjs';
import { AccountService } from 'app/core/auth/account.service';
import { ICart } from 'app/entities/cart/cart.model';
import { HttpResponse } from '@angular/common/http';
import { IBook } from 'app/entities/book/book.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CartService } from 'app/entities/cart/service/cart.service';

@Component({
  selector: 'jhi-cart-view',
  templateUrl: './cart-view.component.html',
  styleUrls: ['./cart-view.component.scss'],
})
export class CartViewComponent implements OnInit {
  account: Account | null = null;
  authSubscription?: Subscription;
  customer?: ICustomer | null;
  cart: ICart | null = null;
  books: IBook[] = [];
  isLoading = true;

  constructor(private accountService: AccountService, private customerService: CustomerService, private cartService: CartService) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.loadAll();
  }

  loadAll(): void {
    this.isLoading = true;
    if (this.account) {
      this.customerService.findByLogin(this.account.login).subscribe(
        (res: HttpResponse<ICart>) => {
          this.isLoading = false;
          this.customer = res.body ?? null;

          console.warn(JSON.stringify(this.customer));
          this.cart = this.customer?.cart ? this.customer.cart : this.cart;
          this.books = this.cart?.books ? this.cart.books : this.books;
        },
        () => {
          this.isLoading = false;
        }
      );
    }
  }

  removeFromCart(book: IBook): void {
    if (this.cart?.books) {
      const books: IBook[] = this.cart.books;
      books.splice(books.indexOf(book, 0));
      this.cart.books = books;
      this.cartService.update(this.cart);
      this.loadAll();
    }
  }
}
