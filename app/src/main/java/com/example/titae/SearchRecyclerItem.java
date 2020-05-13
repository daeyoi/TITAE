package com.example.titae;

import android.graphics.Region;
import android.graphics.drawable.Drawable;

public class SearchRecyclerItem {

    //Bank 테이블
    private String bankName;
    private String financialSphere;
    private String Region;

    //InstallmentSavings + Deposit 테이블
    private String productName;
    private float rate;
    private String target;
    private String reservingMethod;
    private String calMethod;

    public String getBankName() {
        return bankName;
    }

    public String getFinancialSphere() {
        return financialSphere;
    }

    public String getRegion() {
        return Region;
    }

    public String getProductName() {
        return productName;
    }

    public float getRate() {
        return rate;
    }

    public String getTarget() {
        return target;
    }

    public String getReservingMethod() {
        return reservingMethod;
    }

    public String getCalMethod() {
        return calMethod;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setFinancialSphere(String financialSphere) {
        this.financialSphere = financialSphere;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setReservingMethod(String reservingMethod) {
        this.reservingMethod = reservingMethod;
    }

    public void setCalMethod(String calMethod) {
        this.calMethod = calMethod;
    }
}
