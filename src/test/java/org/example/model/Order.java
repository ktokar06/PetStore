package org.example.model;

import lombok.Data;
import java.time.OffsetDateTime;


@Data
public class Order {
    private Long id;
    private Long petId;
    private Integer quantity;
    private OffsetDateTime shipDate;
    private String status;
    private Boolean complete;
}