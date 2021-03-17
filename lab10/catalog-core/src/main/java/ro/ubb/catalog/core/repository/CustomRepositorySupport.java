package ro.ubb.catalog.core.repository;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** Created by radu. */
@Getter
public abstract class CustomRepositorySupport {
  @PersistenceContext private EntityManager entityManager;
}
