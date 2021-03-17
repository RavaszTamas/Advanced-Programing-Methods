package ro.ubb.catalog.core.service.validators;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.LabProblem;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

/**
 * LabProblemValidator has it's sole purpose to check out whether an instance of LabProblem complies
 * to the requirements for such objects.
 */
@Component
public class LabProblemValidator implements Validator<LabProblem> {

  /**
   * Checks whether the given LabProblem instance is valid.
   *
   * @param entity the LabProblem to be validated
   * @throws ValidatorException if at least one of the criteria for creating the LabProblem is
   *     violated
   */
  @Override
  public void validate(LabProblem entity) throws ValidatorException {
    StringBuilder errorMessage = new StringBuilder();
    if (entity.getProblemNumber() < 0) errorMessage.append("Invalid problem number! ");
    if (entity.getDescription().isEmpty()) errorMessage.append("Invalid description! ");

    if (errorMessage.length() > 0) throw new ValidatorException(errorMessage.toString());
  }
}
