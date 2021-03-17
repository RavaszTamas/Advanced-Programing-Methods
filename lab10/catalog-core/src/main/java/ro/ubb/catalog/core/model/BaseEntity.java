package ro.ubb.catalog.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by radu.
 *
 * <p>lombok
 */
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
  @Id
  //  @TableGenerator(name = "TABLE_GENERATOR", initialValue = 0, allocationSize = 1)
  //  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TABLE_GENERATOR")
  //  @Column(unique = true, nullable = false)
  //  @GeneratedValue
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private ID id;
}
