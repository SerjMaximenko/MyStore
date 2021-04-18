package ru.maximenko.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "documents_product")
@Data
public class DocProduct {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_arrival_head_id")
    private DocArrivalHead docArrivalHead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_moving_head_id")
    private DocMovingHead docMovingHead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_sale_head_id")
    private DocSaleHead docSaleHead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String doctype;


}
