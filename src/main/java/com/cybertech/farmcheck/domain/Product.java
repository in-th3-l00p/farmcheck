package com.cybertech.farmcheck.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @NotNull
    @Column
    private String description;

    @NotNull
    @Column
    private Double price;

    @Column
    private Double old_price;

    @NotNull
    @Column
    private Long stock;

    @OneToMany(mappedBy = "product")
    private Set<OrderProducts> orderProducts;

    @OneToMany(mappedBy = "product")
    private Set<ProductPhotos> productPhotos;

    public Set<ProductPhotos> getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(Set<ProductPhotos> productPhotos) {
        this.productPhotos = productPhotos;
    }

    public Set<OrderProducts> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProducts> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOld_price() {
        return old_price;
    }

    public void setOld_price(Double old_price) {
        this.old_price = old_price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Products{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", old_price=" + old_price +
            ", stock=" + stock +
            '}';
    }
}
