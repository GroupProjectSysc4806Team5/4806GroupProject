import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBookstore, getBookstoreIdentifier } from '../bookstore.model';

export type EntityResponseType = HttpResponse<IBookstore>;
export type EntityArrayResponseType = HttpResponse<IBookstore[]>;

@Injectable({ providedIn: 'root' })
export class BookstoreService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/bookstores');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(bookstore: IBookstore): Observable<EntityResponseType> {
    return this.http.post<IBookstore>(this.resourceUrl, bookstore, { observe: 'response' });
  }

  update(bookstore: IBookstore): Observable<EntityResponseType> {
    return this.http.put<IBookstore>(`${this.resourceUrl}/${getBookstoreIdentifier(bookstore) as number}`, bookstore, {
      observe: 'response',
    });
  }

  partialUpdate(bookstore: IBookstore): Observable<EntityResponseType> {
    return this.http.patch<IBookstore>(`${this.resourceUrl}/${getBookstoreIdentifier(bookstore) as number}`, bookstore, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBookstore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookstore[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBookstoreToCollectionIfMissing(
    bookstoreCollection: IBookstore[],
    ...bookstoresToCheck: (IBookstore | null | undefined)[]
  ): IBookstore[] {
    const bookstores: IBookstore[] = bookstoresToCheck.filter(isPresent);
    if (bookstores.length > 0) {
      const bookstoreCollectionIdentifiers = bookstoreCollection.map(bookstoreItem => getBookstoreIdentifier(bookstoreItem)!);
      const bookstoresToAdd = bookstores.filter(bookstoreItem => {
        const bookstoreIdentifier = getBookstoreIdentifier(bookstoreItem);
        if (bookstoreIdentifier == null || bookstoreCollectionIdentifiers.includes(bookstoreIdentifier)) {
          return false;
        }
        bookstoreCollectionIdentifiers.push(bookstoreIdentifier);
        return true;
      });
      return [...bookstoresToAdd, ...bookstoreCollection];
    }
    return bookstoreCollection;
  }
}
