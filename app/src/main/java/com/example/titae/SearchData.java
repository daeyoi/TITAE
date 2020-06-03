package com.example.titae;

import java.io.Serializable;


/**
 * 쿼리요청을 위한 구조체
 * DB참고해서 값 입력해놨는데 필요 시에 바꿔도 OK
 */
public class SearchData implements Serializable {
    public String financialSphere;
    public String target;
    public String calMethod;  //적립 방식
    public String region;
    public String reservingMethod;
    public int amount;
    public int period;

    public void setFinancialSphere(String financialSphere) {
        this.financialSphere = financialSphere;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setCalMethod(String calMethod) {
        this.calMethod = calMethod;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setReservingMethod(String reservingMethod) {
        this.reservingMethod = reservingMethod;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getFinancialSphere() {
        return financialSphere;
    }

    public String getTarget() {
        return target;
    }

    public String getCalMethod() {
        return calMethod;
    }

    public String getRegion() {
        return region;
    }

    public String getReservingMethod() {
        return reservingMethod;
    }

    public int getAmount() {
        return amount;
    }

    public int getPeriod() {
        return period;
    }
}
