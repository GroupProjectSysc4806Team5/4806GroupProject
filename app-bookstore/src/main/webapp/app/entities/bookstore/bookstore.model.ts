import { IOwner } from 'app/entities/owner/owner.model';
import { IBook } from 'app/entities/book/book.model';

export interface IBookstore {
  id?: number;
  name?: string | null;
  owner?: IOwner | null;
  books?: IBook[] | null;
}

export class Bookstore implements IBookstore {
  constructor(public id?: number, public name?: string | null, public owner?: IOwner | null, public books?: IBook[] | null) {}
}

export function getBookstoreIdentifier(bookstore: IBookstore): number | undefined {
  return bookstore.id;
}
