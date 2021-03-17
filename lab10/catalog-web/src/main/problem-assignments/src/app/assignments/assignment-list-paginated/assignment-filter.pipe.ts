import {Pipe, PipeTransform} from "@angular/core";
import {Assignment} from "../shared/assignment.model";

@Pipe({name: 'assignmentFilter'})
export class AssignmentFilterPipe implements PipeTransform {
  transform(assignments: Assignment[], searchTerm: string): Assignment[] {
    if (!assignments || !searchTerm) {
      return assignments;
    }
    return assignments.filter(
      assignment =>
        assignment.grade === +searchTerm);
  }
}
