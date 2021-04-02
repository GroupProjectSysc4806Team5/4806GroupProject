import { IBookstore } from 'app/entities/bookstore/bookstore.model';
import { ICart } from 'app/entities/cart/cart.model';

export interface IBook {
  id?: number;
  author?: string | null;
  available?: boolean | null;
  bookName?: string | null;
  description?: string | null;
  isbn?: string | null;
  picture?: string | null;
  publisher?: string | null;
  bookstore?: IBookstore | null;
  carts?: ICart[] | null;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public author?: string | null,
    public available?: boolean | null,
    public bookName?: string | null,
    public description?: string | null,
    public isbn?: string | null,
    public picture?: string | null,
    public publisher?: string | null,
    public bookstore?: IBookstore | null,
    public carts?: ICart[] | null
  ) {
    this.available = this.available ?? false;
  }
}

export function getBookIdentifier(book: IBook): number | undefined {
  return book.id;
}
