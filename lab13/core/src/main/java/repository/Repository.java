package repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import ro.ubb.catalog.core.model.BaseEntity;

import java.io.Serializable;

/** Created by radu. */
@NoRepositoryBean
public interface Repository<T extends BaseEntity<ID>, ID extends Serializable>
    extends PagingAndSortingRepository<T, ID> {}
