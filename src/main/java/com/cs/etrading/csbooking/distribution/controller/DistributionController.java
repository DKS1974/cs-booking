package com.cs.etrading.csbooking.distribution.controller;

import com.cs.etrading.csbooking.distribution.model.Distribution;
import com.cs.etrading.csbooking.distribution.service.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DistributionController {

    @Autowired
    private DistributionService distributionService ;

    @GetMapping("/distribution")
    @ResponseBody
    public List<Distribution> getDistributionsByBookId(@RequestParam(required = true) Long bookId) {
        return distributionService.getDistributionsByBookId(bookId) ;
    }
}
