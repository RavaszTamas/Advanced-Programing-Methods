import {Pipe, PipeTransform} from "@angular/core";
import {LabProblem} from "../shared/labproblems.model";

@Pipe({name: 'labProblemFilter'})
export class LabProblemFilterPipe implements PipeTransform {
  transform(students: LabProblem[], searchTerm: string): LabProblem[] {
    if (!students || !searchTerm) {
      return students;
    }
    return students.filter(
      student =>
        student.description.toLocaleLowerCase().indexOf(
          searchTerm.toLocaleLowerCase()) !== -1);
  }
}
