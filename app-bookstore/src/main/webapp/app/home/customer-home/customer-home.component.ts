import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { Router } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-customer-home',
  templateUrl: './customer-home.component.html',
  styleUrls: ['./customer-home.component.scss'],
})
export class CustomerHomeComponent implements OnInit {
  account: Account | null = null;
  authSubscription?: Subscription;

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }
}
