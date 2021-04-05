import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { CustomerHomeComponent } from './customer-home/customer-home.component';
import { OwnerHomeComponent } from './owner-home/owner-home.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, CustomerHomeComponent, OwnerHomeComponent],
})
export class HomeModule {}
