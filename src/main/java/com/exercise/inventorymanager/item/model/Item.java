package com.exercise.inventorymanager.item.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @PositiveOrZero
    private Integer quantity;
    @NotNull
    @Positive
    private Double value;
}
