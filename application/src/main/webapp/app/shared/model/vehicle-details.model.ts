export const enum Transmission {
  AUTOMATIC = 'AUTOMATIC',
  MANUAL = 'MANUAL'
}

export const enum Fuel {
  PETROL = 'PETROL',
  DIESIL = 'DIESIL',
  HYBRID = 'HYBRID'
}

export interface IVehicleDetails {
  id?: string;
  manufacturer?: string;
  color?: string;
  transmission?: Transmission;
  fuel?: Fuel;
  modelId?: string;
}

export const defaultValue: Readonly<IVehicleDetails> = {};
