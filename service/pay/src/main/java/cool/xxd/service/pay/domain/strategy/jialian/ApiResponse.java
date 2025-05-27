package cool.xxd.service.pay.domain.strategy.jialian;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Integer errCode;
    private String errMessage;
    private Boolean success;
    private String code;
    private String msg;
    private String traceId;
    private T data;
}