import { IFeedBack } from 'app/shared/model//feed-back.model';

export interface IRating {
  id?: string;
  commulative?: number;
  feedbacks?: IFeedBack[];
}

export const defaultValue: Readonly<IRating> = {};
