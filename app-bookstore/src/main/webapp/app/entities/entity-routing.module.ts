import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'bookstore',
        data: { pageTitle: 'amazinBookstoreApp.bookstore.home.title' },
        loadChildren: () => import('./bookstore/bookstore.module').then(m => m.BookstoreModule),
      },
      {
        path: 'book',
        data: { pageTitle: 'amazinBookstoreApp.book.home.title' },
        loadChildren: () => import('./book/book.module').then(m => m.BookModule),
      },
      {
        path: 'cart',
        data: { pageTitle: 'amazinBookstoreApp.cart.home.title' },
        loadChildren: () => import('./cart/cart.module').then(m => m.CartModule),
      },
      {
        path: 'owner',
        data: { pageTitle: 'amazinBookstoreApp.owner.home.title' },
        loadChildren: () => import('./owner/owner.module').then(m => m.OwnerModule),
      },
      {
        path: 'sale',
        data: { pageTitle: 'amazinBookstoreApp.sale.home.title' },
        loadChildren: () => import('./sale/sale.module').then(m => m.SaleModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'amazinBookstoreApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
