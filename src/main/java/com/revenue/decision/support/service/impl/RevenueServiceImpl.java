package com.revenue.decision.support.service.impl;

import com.revenue.decision.support.dao.RevenueHiveDao;
import com.revenue.decision.support.model.Revenue;
import com.revenue.decision.support.service.IRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Benedict Jin on 2016/4/3.
 */
@Service("revenueService")
public class RevenueServiceImpl implements IRevenueService {

    @Autowired
    private RevenueHiveDao revenueHiveDao;

    @Override
    public int insert(Revenue record) {
        return 0;
    }

    @Override
    public List<Revenue> queryTop10() {
        return revenueHiveDao.queryTop10();
    }
}
