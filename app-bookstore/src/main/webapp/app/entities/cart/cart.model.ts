import { IUser } from 'app/entities/user/user.model';
import { IBook } from 'app/entities/book/book.model';
import { ISale } from 'app/entities/sale/sale.model';

export interface ICart {
  id?: number;
  user?: IUser | null;
  books?: IBook[] | null;
  sale?: ISale | null;
}

export class Cart implements ICart {
  constructor(public id?: number, public user?: IUser | null, public books?: IBook[] | null, public sale?: ISale | null) {}
}

export function getCartIdentifier(cart: ICart): number | undefined {
  return cart.id;
}
