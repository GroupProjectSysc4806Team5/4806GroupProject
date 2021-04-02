import { ICart } from 'app/entities/cart/cart.model';

export interface ISale {
  id?: number;
  cart?: ICart | null;
}

export class Sale implements ISale {
  constructor(public id?: number, public cart?: ICart | null) {}
}

export function getSaleIdentifier(sale: ISale): number | undefined {
  return sale.id;
}
