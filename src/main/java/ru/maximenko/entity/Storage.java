package ru.maximenko.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "storages")
public class Storage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "storage")
    private List<DocArrivalHead> docArrivalHeads;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "storage")
    private List<DocMovingHead> docMovingHeads;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "storage")
    private List<DocSaleHead> docSaleHeads;

    public Storage() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<DocArrivalHead> getDocArrivalHeads() {
        return this.docArrivalHeads;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDocArrivalHeads(List<DocArrivalHead> docArrivalHeads) {
        this.docArrivalHeads = docArrivalHeads;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Storage)) return false;
        final Storage other = (Storage) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$docArrivalHeads = this.getDocArrivalHeads();
        final Object other$docArrivalHeads = other.getDocArrivalHeads();
        if (this$docArrivalHeads == null ? other$docArrivalHeads != null : !this$docArrivalHeads.equals(other$docArrivalHeads))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Storage;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $docArrivalHeads = this.getDocArrivalHeads();
        result = result * PRIME + ($docArrivalHeads == null ? 43 : $docArrivalHeads.hashCode());
        return result;
    }

    public String toString() {
        return "Storage(id=" + this.getId() + ", name=" + this.getName() + ")";
    }
}
