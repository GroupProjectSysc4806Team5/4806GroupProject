import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { BookstoreComponent } from './list/bookstore.component';
import { BookstoreDetailComponent } from './detail/bookstore-detail.component';
import { BookstoreUpdateComponent } from './update/bookstore-update.component';
import { BookstoreDeleteDialogComponent } from './delete/bookstore-delete-dialog.component';
import { BookstoreRoutingModule } from './route/bookstore-routing.module';

@NgModule({
  imports: [SharedModule, BookstoreRoutingModule],
  declarations: [BookstoreComponent, BookstoreDetailComponent, BookstoreUpdateComponent, BookstoreDeleteDialogComponent],
  entryComponents: [BookstoreDeleteDialogComponent],
})
export class BookstoreModule {}
