package com.bruce.open.mortgage.IPayStrategy;

import com.bruce.open.mortgage.Model.PayResult;

/**
 * Created by qizhenghao on 16/6/21.
 */
public class PayContext {

    private IPayStrategy iPayStrategy;

    public PayContext(IPayStrategy iPayStrategy) {
        this.iPayStrategy = iPayStrategy;
    }

    public PayResult operate() {
        return iPayStrategy.calculate();
    }

}
