package com.magic.picshow.app;

/**
 * Created by snowwolf on 17/3/21.
 */

public class WXPayEvent {
    public static final int PAY_Ok = 0;
    public static final int PAY_ERR = 1;

    private int payResult;
    public WXPayEvent(int payResult){
        this.payResult = payResult;
    }

    public int getPayResult() {
        return payResult;
    }

    public void setPayResult(int payResult) {
        this.payResult = payResult;
    }
}
