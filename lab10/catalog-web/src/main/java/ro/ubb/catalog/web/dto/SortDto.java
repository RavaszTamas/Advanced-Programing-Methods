package ro.ubb.catalog.web.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class SortDto {

  List<SortColumnDto> sortColumnDtoList;
}
