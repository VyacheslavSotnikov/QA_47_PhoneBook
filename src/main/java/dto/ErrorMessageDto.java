package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ErrorMessageDto {

    private String timestamp;
    private int status;
    private String error;
    private Object message;
    private String path;
}
