export interface IMetaData {
  id?: string;
  name?: string;
  value?: string;
  vehicleId?: string;
}

export const defaultValue: Readonly<IMetaData> = {};
