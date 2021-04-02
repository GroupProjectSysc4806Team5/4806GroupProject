import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISale, getSaleIdentifier } from '../sale.model';

export type EntityResponseType = HttpResponse<ISale>;
export type EntityArrayResponseType = HttpResponse<ISale[]>;

@Injectable({ providedIn: 'root' })
export class SaleService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/sales');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(sale: ISale): Observable<EntityResponseType> {
    return this.http.post<ISale>(this.resourceUrl, sale, { observe: 'response' });
  }

  update(sale: ISale): Observable<EntityResponseType> {
    return this.http.put<ISale>(`${this.resourceUrl}/${getSaleIdentifier(sale) as number}`, sale, { observe: 'response' });
  }

  partialUpdate(sale: ISale): Observable<EntityResponseType> {
    return this.http.patch<ISale>(`${this.resourceUrl}/${getSaleIdentifier(sale) as number}`, sale, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISale>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISale[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSaleToCollectionIfMissing(saleCollection: ISale[], ...salesToCheck: (ISale | null | undefined)[]): ISale[] {
    const sales: ISale[] = salesToCheck.filter(isPresent);
    if (sales.length > 0) {
      const saleCollectionIdentifiers = saleCollection.map(saleItem => getSaleIdentifier(saleItem)!);
      const salesToAdd = sales.filter(saleItem => {
        const saleIdentifier = getSaleIdentifier(saleItem);
        if (saleIdentifier == null || saleCollectionIdentifiers.includes(saleIdentifier)) {
          return false;
        }
        saleCollectionIdentifiers.push(saleIdentifier);
        return true;
      });
      return [...salesToAdd, ...saleCollection];
    }
    return saleCollection;
  }
}
