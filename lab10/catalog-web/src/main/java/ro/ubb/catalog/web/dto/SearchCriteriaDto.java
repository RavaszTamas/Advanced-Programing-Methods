package ro.ubb.catalog.web.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Data
public class SearchCriteriaDto {
  String searchString;
  Integer pageNumber;
  Integer pageSize;
}
