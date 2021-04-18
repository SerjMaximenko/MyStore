package ru.maximenko.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductLeftDto {

    private String article;

    private String name;

    private Long quantity;
}
