package cool.xxd.infra.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageRequest {
    private Long page;
    private Long size;

    public PageRequest() {
        this.page = 1L;
        this.size = 10L;
    }

    private PageRequest(long page, long size) {
        this.page = page;
        this.size = size;
    }

    public static PageRequest of() {
        return of(1L, 10L);
    }

    public static PageRequest of(long page, long size) {
        return new PageRequest(page, size);
    }

    public <T> IPage<T> toIPage() {
        return Page.of(page, size);
    }

    public void nextPage() {
        this.page += 1;
    }
}
