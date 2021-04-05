import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookstoresComponent } from './bookstores/bookstores.component';
import { BookstoreViewComponent } from './bookstore-view/bookstore-view.component';
import { BookViewComponent } from './book-view/book-view.component';
import { CartViewComponent } from './cart-view/cart-view.component';
import { RouterModule } from '@angular/router';
import { MAIN_VIEW_ROUTE } from 'app/main-view/main-view.route';
import { SharedModule } from 'app/shared/shared.module';

@NgModule({
  declarations: [BookstoresComponent, BookstoreViewComponent, BookViewComponent, CartViewComponent],
  imports: [CommonModule, RouterModule.forChild(MAIN_VIEW_ROUTE), SharedModule],
  exports: [RouterModule],
})
export class MainViewModule {}
