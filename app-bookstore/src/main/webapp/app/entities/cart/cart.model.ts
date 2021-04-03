import { ICustomer } from 'app/entities/customer/customer.model';
import { IBook } from 'app/entities/book/book.model';

export interface ICart {
  id?: number;
  customer?: ICustomer | null;
  books?: IBook[] | null;
}

export class Cart implements ICart {
  constructor(public id?: number, public customer?: ICustomer | null, public books?: IBook[] | null) {}
}

export function getCartIdentifier(cart: ICart): number | undefined {
  return cart.id;
}
