package me.pnodder.inventoryservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

public record Item(
        @JsonProperty(value = "id")
        @Null
        String id,
        @JsonProperty(value = "name")
        @NotBlank
        String name,
        @JsonProperty(value = "manufacturer")
        @NotBlank
        String manufacturer,
        @JsonProperty(value = "price")
        @Null
        double price,
        @JsonProperty(value = "stockCount")
        @Null
        int stockCount
) {
}
