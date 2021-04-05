import { Routes } from '@angular/router';

import { CartViewComponent } from './cart-view/cart-view.component';
import { BookstoresComponent } from './bookstores/bookstores.component';
import { BookstoreViewComponent } from './bookstore-view/bookstore-view.component';
import { BookViewComponent } from './book-view/book-view.component';

export const MAIN_VIEW_ROUTE: Routes = [
  {
    path: 'my-cart',
    component: CartViewComponent,
    data: {
      authorities: ['ROLE_CUSTOMER'],
      pageTitle: 'main-view.cart.title',
    },
  },
  {
    path: 'view-bookstores',
    component: BookstoresComponent,
  },
  {
    path: 'view-bookstore/:id',
    component: BookstoreViewComponent,
  },
  {
    path: 'view-book/:id',
    component: BookViewComponent,
  },
];
