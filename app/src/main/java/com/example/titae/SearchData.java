package com.example.titae;

import java.io.Serializable;


/**
 * 쿼리요청을 위한 구조체
 * DB참고해서 값 입력해놨는데 필요 시에 바꿔도 OK
 */
public class SearchData implements Serializable {
    public String financialSphere;
    public String target;
    public String reservingmethod;  //적립 방식
    public String region;
    public int amount;
    public int period;

    public void setFinancialSphere(String financialSphere) {
        this.financialSphere = financialSphere;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setReservingmethod(String reservingmethod) {
        this.reservingmethod = reservingmethod;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getReservingmethod() {
        return reservingmethod;
    }

    public String getRegion() {
        return region;
    }

    public int getAmount() {
        return amount;
    }

    public int getPeriod() {
        return period;
    }
}
