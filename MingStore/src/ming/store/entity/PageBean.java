package ming.store.entity;

import java.util.List;

/**
 * Created by Ming on 2017/8/28.
 */
public class PageBean <T>{
    private int currentCount;
    private int totalCount;
    private int currentPage;
    private int totalPage;

    @Override
    public String toString() {
        return "PageBean{" +
                "currentCount=" + currentCount +
                ", totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", data=" + data +
                '}';
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    private List<T> data;



}
