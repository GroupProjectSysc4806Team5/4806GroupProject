import { ISale } from 'app/entities/sale/sale.model';
import { IBook } from 'app/entities/book/book.model';

export interface ICart {
  id?: number;
  sale?: ISale | null;
  books?: IBook[] | null;
}

export class Cart implements ICart {
  constructor(public id?: number, public sale?: ISale | null, public books?: IBook[] | null) {}
}

export function getCartIdentifier(cart: ICart): number | undefined {
  return cart.id;
}
