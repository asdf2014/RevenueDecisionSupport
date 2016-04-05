package com.revenue.decision.support.controller;

import com.revenue.decision.support.model.Revenue;
import com.revenue.decision.support.service.IRevenueService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/revenue")
public class RevenueController {

    @Resource
    private IRevenueService customerService;

    @RequestMapping(value = "/top10", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String queryTopN(HttpServletRequest request, Model model) {

        List<Revenue> revenues = customerService.queryTop10();
        if (revenues == null || revenues.size() > 0) {
            model.addAttribute("revenue", revenues.get(0));
        } else {
            model.addAttribute("error", "There are nothing in DataBase.");
            return "error";
        }
        return "revenue";
    }
}