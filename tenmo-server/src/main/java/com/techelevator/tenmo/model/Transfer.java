package com.techelevator.tenmo.model;

public class Transfer {
    private int transferId;
    private int fromUser;
    private int toUser;
    private double transferAmount;
    private String status;
    private String transferType;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Transfer(int transferId, int fromUser, int toUser, double transferAmount, String status, String transferType) {
        this.transferId = transferId;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.transferAmount = transferAmount;
        this.status = status;
        this.transferType = transferType;
    }
}
