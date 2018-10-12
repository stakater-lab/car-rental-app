export interface IRate {
  id?: string;
  hourly?: number;
  hourlyWithoutDriver?: number;
  daily?: number;
  dailyWithoutDriver?: number;
}

export const defaultValue: Readonly<IRate> = {};
