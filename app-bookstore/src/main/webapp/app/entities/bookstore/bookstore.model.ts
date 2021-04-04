import { IBook } from 'app/entities/book/book.model';
import { IOwner } from 'app/entities/owner/owner.model';

export interface IBookstore {
  id?: number;
  name?: string | null;
  books?: IBook[] | null;
  owner?: IOwner | null;
}

export class Bookstore implements IBookstore {
  constructor(public id?: number, public name?: string | null, public books?: IBook[] | null, public owner?: IOwner | null) {}
}

export function getBookstoreIdentifier(bookstore: IBookstore): number | undefined {
  return bookstore.id;
}
