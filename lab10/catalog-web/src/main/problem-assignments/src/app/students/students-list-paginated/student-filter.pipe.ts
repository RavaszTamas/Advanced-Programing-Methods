import {Pipe, PipeTransform} from "@angular/core";
import {Student} from "../shared/student.model";

@Pipe({name: 'studentFilter'})
export class StudentFilterPipe implements PipeTransform {
  transform(students: Student[], searchTerm: string): Student[] {
    if (!students || !searchTerm) {
      return students;
    }
    return students.filter(
      student =>
        student.name.toLocaleLowerCase().indexOf(
          searchTerm.toLocaleLowerCase()) !== -1);
  }
}
