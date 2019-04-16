package cn.itcast.core.pojo.item;

import java.io.Serializable;
import java.util.Objects;

public class ItemCat implements Serializable {
    /**
     * 类目ID
     */
    private Long id;

    /**
     * 父类目ID=0时，代表的是一级的类目
     */
    private Long parentId;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 类型id
     */
    private Long typeId;

    /**
     * 申请状态
     */
    private String itemCatStatus;


    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getItemCatStatus() {
        return itemCatStatus;
    }

    public void setItemCatStatus(String itemCatStatus) {
        this.itemCatStatus = itemCatStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCat itemCat = (ItemCat) o;
        return Objects.equals(id, itemCat.id) &&
                Objects.equals(parentId, itemCat.parentId) &&
                Objects.equals(name, itemCat.name) &&
                Objects.equals(typeId, itemCat.typeId) &&
                Objects.equals(itemCatStatus, itemCat.itemCatStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, parentId, name, typeId, itemCatStatus);
    }

    @Override
    public String toString() {
        return "ItemCat{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", itemCatStatus='" + itemCatStatus + '\'' +
                '}';
    }


}