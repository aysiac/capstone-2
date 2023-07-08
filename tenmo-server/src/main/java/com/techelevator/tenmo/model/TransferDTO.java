package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for passing the transfer info from client
 */
public class TransferDTO {
    @NotNull(message = "fromUserId should not empty.")
    private int fromUserId;
    @NotNull(message = "toUserId should not empty.")
    private int toUserId;
    @NotNull(message = "amount should not empty.")
    @Min(value = 1)
    private double amount;
    @NotNull(message = "transferType should not empty.")
    private String transferType;

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserName) {
        this.fromUserId = fromUserName;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserName) {
        this.toUserId = toUserName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
}
