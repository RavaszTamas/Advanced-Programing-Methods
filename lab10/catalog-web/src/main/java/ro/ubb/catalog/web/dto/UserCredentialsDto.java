package ro.ubb.catalog.web.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class UserCredentialsDto {

    private List<String> role;

}
