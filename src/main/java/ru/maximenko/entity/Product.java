package ru.maximenko.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String article;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<DocProduct> docProducts;

    private Double lastPurchasePrice;

    private Double lastSalePrice;

    public Product() {
    }


    public Long getId() {
        return this.id;
    }

    public String getArticle() {
        return this.article;
    }

    public String getName() {
        return this.name;
    }

    public List<DocProduct> getDocProducts() {
        return this.docProducts;
    }

    public Double getLastPurchasePrice() {
        return this.lastPurchasePrice;
    }

    public Double getLastSalePrice() {
        return this.lastSalePrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDocProducts(List<DocProduct> docProducts) {
        this.docProducts = docProducts;
    }

    public void setLastPurchasePrice(Double lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public void setLastSalePrice(Double lastsalePrice) {
        this.lastSalePrice = lastsalePrice;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$article = this.getArticle();
        final Object other$article = other.getArticle();
        if (this$article == null ? other$article != null : !this$article.equals(other$article)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$docArrivalProducts = this.getDocProducts();
        final Object other$docArrivalProducts = other.getDocProducts();
        if (this$docArrivalProducts == null ? other$docArrivalProducts != null : !this$docArrivalProducts.equals(other$docArrivalProducts))
            return false;
        final Object this$lastPurchasePrice = this.getLastPurchasePrice();
        final Object other$lastPurchasePrice = other.getLastPurchasePrice();
        if (this$lastPurchasePrice == null ? other$lastPurchasePrice != null : !this$lastPurchasePrice.equals(other$lastPurchasePrice))
            return false;
        final Object this$lastsalePrice = this.getLastSalePrice();
        final Object other$lastsalePrice = other.getLastSalePrice();
        if (this$lastsalePrice == null ? other$lastsalePrice != null : !this$lastsalePrice.equals(other$lastsalePrice))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $article = this.getArticle();
        result = result * PRIME + ($article == null ? 43 : $article.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $docArrivalProducts = this.getDocProducts();
        result = result * PRIME + ($docArrivalProducts == null ? 43 : $docArrivalProducts.hashCode());
        final Object $lastPurchasePrice = this.getLastPurchasePrice();
        result = result * PRIME + ($lastPurchasePrice == null ? 43 : $lastPurchasePrice.hashCode());
        final Object $lastsalePrice = this.getLastSalePrice();
        result = result * PRIME + ($lastsalePrice == null ? 43 : $lastsalePrice.hashCode());
        return result;
    }

    public String toString() {
        return "Product(id=" + this.getId() + ", article=" + this.getArticle() + ", name=" + this.getName() + ", lastPurchasePrice=" + this.getLastPurchasePrice() + ", lastsalePrice=" + this.getLastSalePrice() + ")";
    }
}
