package cool.xxd.mapstruct.page;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private List<T> data;
    private long total;

    private PageResult() {
    }

    public static <T> PageResult<T> of() {
        return of(List.of(), 0);
    }

    public static <T> PageResult<T> of(List<T> data, long total) {
        var pageResult = new PageResult<T>();
        pageResult.data = data;
        pageResult.total = total;
        return pageResult;
    }
}
