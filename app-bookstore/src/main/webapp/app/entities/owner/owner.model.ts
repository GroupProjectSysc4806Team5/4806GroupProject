import { IBookstore } from 'app/entities/bookstore/bookstore.model';

export interface IOwner {
  id?: number;
  name?: string | null;
  password?: string | null;
  stores?: IBookstore[] | null;
}

export class Owner implements IOwner {
  constructor(public id?: number, public name?: string | null, public password?: string | null, public stores?: IBookstore[] | null) {}
}

export function getOwnerIdentifier(owner: IOwner): number | undefined {
  return owner.id;
}
