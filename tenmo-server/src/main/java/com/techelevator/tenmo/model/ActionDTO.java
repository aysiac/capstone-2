package com.techelevator.tenmo.model;

/**
 * DTO for handling user's pending request.
 */
public class ActionDTO {
    private int transferId;
    private String actionName;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
