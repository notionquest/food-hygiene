package com.food.hygiene.model;

import java.io.Serializable;

public class Authority implements Serializable {
    private static final long serialVersionUID = 2007156672078634545L;
    private String localAuthorityId;
    private String calculatorName;

    public String getLocalAuthorityId() {
        return localAuthorityId;
    }

    public void setLocalAuthorityId(String localAuthorityId) {
        this.localAuthorityId = localAuthorityId;
    }

    public String getCalculatorName() {
        return calculatorName;
    }

    public void setCalculatorName(String calculatorName) {
        this.calculatorName = calculatorName;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "localAuthorityId='" + localAuthorityId + '\'' +
                ", calculatorName='" + calculatorName + '\'' +
                '}';
    }
}
