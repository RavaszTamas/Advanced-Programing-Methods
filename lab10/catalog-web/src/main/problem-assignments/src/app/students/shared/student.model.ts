export class Student {
  id: Number;
  serialNumber: string;
  name: string;
  groupNumber: number
}

export class SortColumnDto {

  direction: string;
  column: string;
}

export class SortDto {

  sortColumnDtoList: SortColumnDto[]
}


export class SearchCriteria {
  key: string
  operation: string;
  value: string;

}

export class SearchCriterias {
  criterias: SearchCriteria[];
  pageNumber: number;
  pageSize: number;
}

export class Students {
  students: Student[]
}
