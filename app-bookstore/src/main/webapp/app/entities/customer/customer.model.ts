import { ICart } from 'app/entities/cart/cart.model';
import { IUser } from 'app/entities/user/user.model';

export interface ICustomer {
  id?: number;
  cart?: ICart | null;
  user?: IUser | null;
}

export class Customer implements ICustomer {
  constructor(public id?: number, public cart?: ICart | null, public user?: IUser | null) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
