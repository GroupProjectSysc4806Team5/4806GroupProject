export interface ISale {
  id?: number;
}

export class Sale implements ISale {
  constructor(public id?: number) {}
}

export function getSaleIdentifier(sale: ISale): number | undefined {
  return sale.id;
}
