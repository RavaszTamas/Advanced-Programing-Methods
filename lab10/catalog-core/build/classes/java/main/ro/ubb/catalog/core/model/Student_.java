package ro.ubb.catalog.core.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Student.class)
public abstract class Student_ extends ro.ubb.catalog.core.model.BaseEntity_ {

	public static volatile SingularAttribute<Student, String> serialNumber;
	public static volatile SetAttribute<Student, Assignment> assignments;
	public static volatile SingularAttribute<Student, String> name;
	public static volatile SingularAttribute<Student, Integer> groupNumber;

	public static final String SERIAL_NUMBER = "serialNumber";
	public static final String ASSIGNMENTS = "assignments";
	public static final String NAME = "name";
	public static final String GROUP_NUMBER = "groupNumber";

}

