import { IUser } from 'app/entities/user/user.model';
import { IBookstore } from 'app/entities/bookstore/bookstore.model';

export interface IOwner {
  id?: number;
  user?: IUser | null;
  bookstores?: IBookstore[] | null;
}

export class Owner implements IOwner {
  constructor(public id?: number, public user?: IUser | null, public bookstores?: IBookstore[] | null) {}
}

export function getOwnerIdentifier(owner: IOwner): number | undefined {
  return owner.id;
}
