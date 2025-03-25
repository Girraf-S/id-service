package by.hembar.idservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DefaultResponse {
    private String errorTittle;
    private String errorDescription;
    private int errorCode;
}
