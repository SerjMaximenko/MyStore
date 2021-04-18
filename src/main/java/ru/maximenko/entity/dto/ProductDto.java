package ru.maximenko.entity.dto;


import lombok.Data;

@Data
public class ProductDto {

    private Long id;

    private String article;

    private String name;

    private Double lastPurchasePrice;

    private Double lastsalePrice;

}
