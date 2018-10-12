export interface IAddress {
  id?: string;
  longitude?: number;
  latitude?: number;
  area?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  province?: string;
  providerId?: string;
}

export const defaultValue: Readonly<IAddress> = {};
