package com.card_inventory.cards.DTOs;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class AddCardsRequest {

    @NotNull(message = "Field cannot be empty or null")
    private Long cardId;
    @NotNull(message = "Field cannot be empty or null")
    private int batchId;
    @NotNull(message = "Field cannot be empty or null")
    private String cardRange;
    @NotNull(message = "Field cannot be empty or null")
    private int quantityReceived;
    @NotNull(message = "Field cannot be empty or null")
    private Date expiryDate;
    @NotNull(message = "Field cannot be empty or null")
    private String vendorName;
    @NotNull(message = "Field cannot be empty or null")
    private String stockStatus;
    @NotNull(message = "Field cannot be empty or null")
    private String cardType;

}
