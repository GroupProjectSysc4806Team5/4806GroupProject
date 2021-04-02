import { IOwner } from 'app/entities/owner/owner.model';

export interface IBookstore {
  id?: number;
  name?: string | null;
  owners?: IOwner[] | null;
}

export class Bookstore implements IBookstore {
  constructor(public id?: number, public name?: string | null, public owners?: IOwner[] | null) {}
}

export function getBookstoreIdentifier(bookstore: IBookstore): number | undefined {
  return bookstore.id;
}
