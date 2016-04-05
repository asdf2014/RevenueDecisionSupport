package com.revenue.decision.support.service;

import com.revenue.decision.support.model.Revenue;

import java.util.List;

/**
 * Created by Benedict Jin on 2016/4/3.
 */
public interface IRevenueService {

    int insert(Revenue record);

    List<Revenue> queryTop10();
}
