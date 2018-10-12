import { IAddress } from 'app/shared/model//address.model';

export const enum ProviderType {
  Individual = 'Individual',
  Business = 'Business'
}

export interface IProvider {
  id?: string;
  type?: ProviderType;
  name?: string;
  ratings?: number;
  noOfVehicles?: number;
  emailAddress?: string;
  addresses?: IAddress[];
}

export const defaultValue: Readonly<IProvider> = {};
