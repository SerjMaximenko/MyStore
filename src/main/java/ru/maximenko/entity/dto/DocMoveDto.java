package ru.maximenko.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class DocMoveDto {

    private Long id;

    private String item;

    private String storageFromName;

    private String storageToName;

    private List<DocArrivalProductDto> docArrivalProducts;

}
