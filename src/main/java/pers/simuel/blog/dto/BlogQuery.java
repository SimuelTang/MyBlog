package pers.simuel.blog.dto;

/**
 * 用来装载从数据库中查询出的Blog的部分信息，因为这部分信息只在后台显示，所以不用拥有具体内容
 *
 * @Author simuel_tang
 * @Date 2021/4/27
 * @Time 11:07
 */
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
