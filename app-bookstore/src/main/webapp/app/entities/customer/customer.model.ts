import { IUser } from 'app/entities/user/user.model';

export interface ICustomer {
  id?: number;
  user?: IUser | null;
}

export class Customer implements ICustomer {
  constructor(public id?: number, public user?: IUser | null) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
