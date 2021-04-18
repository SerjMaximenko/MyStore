package ru.maximenko.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DocInputDto {

    private Long id;

    private String item;

    private String storageName;

    private List<DocArrivalProductDto> docArrivalProducts;

}
