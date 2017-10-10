import {PaginationCondition} from "./pagination.condition";

export class PaginationRepertory<T> {
  currentIndex: number;
  totalCount: number;
  totalPage: number;
  pageItems: T;

}
