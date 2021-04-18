package ru.maximenko.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "documents_arrival_head")
public class DocArrivalHead {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "docArrivalHead")
    private List<DocProduct> docProducts;

    @NaturalId
    private String item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    public DocArrivalHead() {
    }


    public Long getId() {
        return this.id;
    }

    public List<DocProduct> getDocProducts() {
        return this.docProducts;
    }

    public String getItem() {
        return this.item;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDocProducts(List<DocProduct> docProducts) {
        this.docProducts = docProducts;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DocArrivalHead)) return false;
        final DocArrivalHead other = (DocArrivalHead) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$docArrivalProducts = this.getDocProducts();
        final Object other$docArrivalProducts = other.getDocProducts();
        if (this$docArrivalProducts == null ? other$docArrivalProducts != null : !this$docArrivalProducts.equals(other$docArrivalProducts))
            return false;
        final Object this$item = this.getItem();
        final Object other$item = other.getItem();
        if (this$item == null ? other$item != null : !this$item.equals(other$item)) return false;
        final Object this$storage = this.getStorage();
        final Object other$storage = other.getStorage();
        if (this$storage == null ? other$storage != null : !this$storage.equals(other$storage)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DocArrivalHead;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $docArrivalProducts = this.getDocProducts();
        result = result * PRIME + ($docArrivalProducts == null ? 43 : $docArrivalProducts.hashCode());
        final Object $item = this.getItem();
        result = result * PRIME + ($item == null ? 43 : $item.hashCode());
        final Object $storage = this.getStorage();
        result = result * PRIME + ($storage == null ? 43 : $storage.hashCode());
        return result;
    }

    public String toString() {
        return "DocArrivalHead(id=" + this.getId() + ", item=" + this.getItem() + ", storage=" + this.getStorage() + ")";
    }
}
