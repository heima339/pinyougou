package cn.itcast.core.pojo.template;

import java.io.Serializable;

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
     * 审核状态
     */
    private String status;

    private static final long serialVersionUID = 1L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (specIds != null ? !specIds.equals(that.specIds) : that.specIds != null) return false;
        if (brandIds != null ? !brandIds.equals(that.brandIds) : that.brandIds != null) return false;
        if (customAttributeItems != null ? !customAttributeItems.equals(that.customAttributeItems) : that.customAttributeItems != null)
            return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (specIds != null ? specIds.hashCode() : 0);
        result = 31 * result + (brandIds != null ? brandIds.hashCode() : 0);
        result = 31 * result + (customAttributeItems != null ? customAttributeItems.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}