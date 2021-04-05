jest.mock('app/core/auth/account.service');
jest.mock('@angular/router');

import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { of } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { BookService } from 'app/entities/book/service/book.service';
import { ActivatedRoute } from '@angular/router';

import { BookstoreViewComponent } from './bookstore-view.component';

xdescribe('Component Tests', () => {
  describe('Home Component', () => {
    let comp: BookstoreViewComponent;
    let fixture: ComponentFixture<BookstoreViewComponent>;
    let mockAccountService: AccountService;
    // let mockBookService: BookService;
    // let mockActivatedRoute: ActivatedRoute;

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          declarations: [BookstoreViewComponent],
          providers: [AccountService, BookService, ActivatedRoute],
        })
          .overrideTemplate(BookstoreViewComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(BookstoreViewComponent);
      comp = fixture.componentInstance;
      mockAccountService = TestBed.inject(AccountService);
      mockAccountService.identity = jest.fn(() => of(null));
      mockAccountService.getAuthenticationState = jest.fn(() => of(null));
    });

    it('Should call accountService.getAuthenticationState on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(mockAccountService.getAuthenticationState).toHaveBeenCalled();
    });
  });
});
