package ro.ubb.catalog.core.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LabProblem.class)
public abstract class LabProblem_ extends ro.ubb.catalog.core.model.BaseEntity_ {

	public static volatile SetAttribute<LabProblem, Assignment> assignments;
	public static volatile SingularAttribute<LabProblem, Integer> problemNumber;
	public static volatile SingularAttribute<LabProblem, String> description;

	public static final String ASSIGNMENTS = "assignments";
	public static final String PROBLEM_NUMBER = "problemNumber";
	public static final String DESCRIPTION = "description";

}

