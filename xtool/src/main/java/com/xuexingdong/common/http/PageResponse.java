package cool.xxd.mapstruct.http;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private int code;
    private String message = "";
    private List<T> data;
    private Long total;

    public static <T> PageResponse<T> ok() {
        return PageResponse.data(List.of(), 0L);
    }

    public static <T> PageResponse<T> data(List<T> data, Long total) {
        var pageResponse = new PageResponse<T>();
        pageResponse.setData(data);
        pageResponse.setTotal(total);
        return pageResponse;
    }
}
