import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBookstore, Bookstore } from '../bookstore.model';
import { BookstoreService } from '../service/bookstore.service';

@Injectable({ providedIn: 'root' })
export class BookstoreRoutingResolveService implements Resolve<IBookstore> {
  constructor(protected service: BookstoreService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookstore> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bookstore: HttpResponse<Bookstore>) => {
          if (bookstore.body) {
            return of(bookstore.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bookstore());
  }
}
