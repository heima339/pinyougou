package cn.itcast.core.pojo.template;

import java.io.Serializable;
import java.util.Objects;

public class TypeTemplate implements Serializable {
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 关联规格
     */
    private String specIds;

    /**
     * 关联品牌
     */
    private String brandIds;

    /**
     * 自定义属性
     */
    private String customAttributeItems;

    /**
     * 申请状态
     */
    private String status;

    private static final long serialVersionUID = 1L;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds == null ? null : specIds.trim();
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds == null ? null : brandIds.trim();
    }

    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems == null ? null : customAttributeItems.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TypeTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specIds='" + specIds + '\'' +
                ", brandIds='" + brandIds + '\'' +
                ", customAttributeItems='" + customAttributeItems + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeTemplate that = (TypeTemplate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(specIds, that.specIds) &&
                Objects.equals(brandIds, that.brandIds) &&
                Objects.equals(customAttributeItems, that.customAttributeItems) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, specIds, brandIds, customAttributeItems, status);
    }
}