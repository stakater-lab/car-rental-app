import { IMetaData } from 'app/shared/model//meta-data.model';

export const enum VehicleAvailability {
  AVAILABLE = 'AVAILABLE',
  BOOKED = 'BOOKED',
  IN_SERVICE = 'IN_SERVICE'
}

export const enum DriverAvailability {
  WITH = 'WITH',
  WITH_OUT = 'WITH_OUT',
  WITH_AND_WITH_OUT = 'WITH_AND_WITH_OUT'
}

export interface IVehicle {
  id?: string;
  ownerId?: number;
  availability?: VehicleAvailability;
  driver?: DriverAvailability;
  imagesContentType?: string;
  images?: any;
  ratingId?: string;
  typeId?: string;
  rateId?: string;
  detailId?: string;
  metaData?: IMetaData[];
}

export const defaultValue: Readonly<IVehicle> = {};
