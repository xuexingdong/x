package cool.xxd.infra.http;

import lombok.Data;

@Data
public class Response<T> {

    private int code;
    private String message = "";
    private T data;

    private Response() {
    }

    public static Response<Void> ok() {
        return new Response<>();
    }

    public static <T> Response<T> data(T t) {
        var response = new Response<T>();
        response.data = t;
        return response;
    }

    public static Response<Void> error(String message) {
        var response = new Response<Void>();
        response.code = -1;
        response.message = message;
        return response;
    }

}