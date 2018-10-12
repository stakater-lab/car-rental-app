export interface IFeedBack {
  id?: string;
  score?: number;
  comment?: string;
  ratingId?: string;
}

export const defaultValue: Readonly<IFeedBack> = {};
