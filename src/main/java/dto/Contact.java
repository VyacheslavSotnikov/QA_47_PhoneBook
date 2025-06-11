package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString

public class Contact {

    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private String description;
}
