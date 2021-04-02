import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BookstoreComponent } from '../list/bookstore.component';
import { BookstoreDetailComponent } from '../detail/bookstore-detail.component';
import { BookstoreUpdateComponent } from '../update/bookstore-update.component';
import { BookstoreRoutingResolveService } from './bookstore-routing-resolve.service';

const bookstoreRoute: Routes = [
  {
    path: '',
    component: BookstoreComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookstoreDetailComponent,
    resolve: {
      bookstore: BookstoreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookstoreUpdateComponent,
    resolve: {
      bookstore: BookstoreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookstoreUpdateComponent,
    resolve: {
      bookstore: BookstoreRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bookstoreRoute)],
  exports: [RouterModule],
})
export class BookstoreRoutingModule {}
