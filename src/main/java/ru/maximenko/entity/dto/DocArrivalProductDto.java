package ru.maximenko.entity.dto;

import lombok.Data;
import ru.maximenko.entity.DocArrivalHead;

@Data
public class DocArrivalProductDto {

    private Long id;

    private DocArrivalHead docArrivalHead;

    private String article;

    private Long quantity;

    private Double price;

}
