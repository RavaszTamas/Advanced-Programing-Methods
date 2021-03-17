package ro.ubb.catalog.core.model;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Assignment.class)
public abstract class Assignment_ extends ro.ubb.catalog.core.model.BaseEntity_ {

  public static volatile SingularAttribute<Assignment, Student> student;
  public static volatile SingularAttribute<Assignment, LabProblem> labProblem;
  public static volatile SingularAttribute<Assignment, Integer> grade;
}
