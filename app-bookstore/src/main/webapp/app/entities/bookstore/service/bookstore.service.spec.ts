import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBookstore, Bookstore } from '../bookstore.model';

import { BookstoreService } from './bookstore.service';

describe('Service Tests', () => {
  describe('Bookstore Service', () => {
    let service: BookstoreService;
    let httpMock: HttpTestingController;
    let elemDefault: IBookstore;
    let expectedResult: IBookstore | IBookstore[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BookstoreService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Bookstore', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Bookstore()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Bookstore', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Bookstore', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new Bookstore()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Bookstore', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Bookstore', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBookstoreToCollectionIfMissing', () => {
        it('should add a Bookstore to an empty array', () => {
          const bookstore: IBookstore = { id: 123 };
          expectedResult = service.addBookstoreToCollectionIfMissing([], bookstore);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bookstore);
        });

        it('should not add a Bookstore to an array that contains it', () => {
          const bookstore: IBookstore = { id: 123 };
          const bookstoreCollection: IBookstore[] = [
            {
              ...bookstore,
            },
            { id: 456 },
          ];
          expectedResult = service.addBookstoreToCollectionIfMissing(bookstoreCollection, bookstore);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Bookstore to an array that doesn't contain it", () => {
          const bookstore: IBookstore = { id: 123 };
          const bookstoreCollection: IBookstore[] = [{ id: 456 }];
          expectedResult = service.addBookstoreToCollectionIfMissing(bookstoreCollection, bookstore);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bookstore);
        });

        it('should add only unique Bookstore to an array', () => {
          const bookstoreArray: IBookstore[] = [{ id: 123 }, { id: 456 }, { id: 63101 }];
          const bookstoreCollection: IBookstore[] = [{ id: 123 }];
          expectedResult = service.addBookstoreToCollectionIfMissing(bookstoreCollection, ...bookstoreArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const bookstore: IBookstore = { id: 123 };
          const bookstore2: IBookstore = { id: 456 };
          expectedResult = service.addBookstoreToCollectionIfMissing([], bookstore, bookstore2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bookstore);
          expect(expectedResult).toContain(bookstore2);
        });

        it('should accept null and undefined values', () => {
          const bookstore: IBookstore = { id: 123 };
          expectedResult = service.addBookstoreToCollectionIfMissing([], null, bookstore, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bookstore);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
