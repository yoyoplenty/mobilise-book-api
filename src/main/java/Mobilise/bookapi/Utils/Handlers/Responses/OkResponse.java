package Mobilise.bookapi.Utils.Handlers.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OkResponse {
    private String message;
    private Integer status;
    private Object data;
}



