package life.cj.community.community.dto;

import life.cj.community.community.model.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/22 15:57
 */
@Data
public class PaginationDTO extends Page {

    private Object list;

    private boolean showToFirst;

    private boolean showToLast;

    private boolean hasPrevious;

    private boolean hasNext;

    private List<Integer> pages = new ArrayList<>();

    public void setParam(Integer count, Page page) {
        this.setPageNum(page.getPageNum());
        this.setPageSize(page.getPageSize());
        this.setTotal(count % page.getPageSize() == 0 ?
                count / page.getPageSize() :
                count / page.getPageSize() + 1);

        if (getPageNum() > getTotal()) {
            this.setPageNum(getTotal());
        }

        if (getPageNum() < 1) {
            this.setPageNum(1);
        }

        pages.add(getPageNum());
        for (int i = 1; i <= 3; i++) {
            if (getPageNum() - i > 0) {
                pages.add(0, getPageNum() - i);
            }

            if (getPageNum() + i <= getTotal()) {
                pages.add(getPageNum() + i);
            }
        }

        //是否展示上一页
        if (getPageNum() == 1) {
            hasPrevious = false;
        } else {
            hasPrevious = true;
        }
        //是否显示下一页
        if (getPageNum() == getTotal()) {
            hasNext = false;
        } else {
            hasNext = true;
        }

        //是否可跳转第一页
        if (pages.contains(1)) {
            showToFirst = false;
        } else {
            showToFirst = true;
        }

        //是否可跳转最后一页
        if (pages.contains(getTotal())) {
            showToLast = false;
        } else {
            showToLast = true;
        }
    }
}
