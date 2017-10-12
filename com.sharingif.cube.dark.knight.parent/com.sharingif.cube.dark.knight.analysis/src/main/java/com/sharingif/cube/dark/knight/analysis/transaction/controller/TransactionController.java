package com.sharingif.cube.dark.knight.analysis.transaction.controller;

import com.sharingif.cube.core.handler.bind.annotation.PathVariable;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.Transaction;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionDateTimeStatistics;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionDay;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionVolumeDay;
import com.sharingif.cube.dark.knight.analysis.transaction.service.TransactionService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.bson.Document;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交易信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/29 下午5:30
 */
@Controller
@RequestMapping(value="transaction")
public class TransactionController {

    private TransactionService transactionService;

    @Resource
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value="list", method= RequestMethod.POST)
    public PaginationRepertory<Document> list(PaginationCondition<Transaction> paginationCondition) {
        return transactionService.getList(paginationCondition);
    }

    @RequestMapping(value="details/{transUniqueId}", method= RequestMethod.GET)
    public List<Document> details(@PathVariable("transUniqueId") String transUniqueId) {
        return transactionService.getDetailsByTransUniqueId(transUniqueId);
    }

    @RequestMapping(value="volume/day", method= RequestMethod.GET)
    public TransactionVolumeDay volumeDay() {
        return transactionService.getTransactionVolumeDay();
    }

    @RequestMapping(value="day", method= RequestMethod.GET)
    public TransactionDay day() {
        return transactionService.getTransactionDay();
    }

    @RequestMapping(value="statistics/day/hour", method= RequestMethod.GET)
    public List<TransactionDateTimeStatistics> statisticsByDayHour() {
        return transactionService.statisticsByDayHour();
    }
}
