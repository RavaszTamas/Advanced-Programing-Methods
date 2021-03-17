package model.specifications;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
