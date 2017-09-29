package com.sharingif.cube.dark.knight.analysis.transaction.controller;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import com.sharingif.cube.dark.knight.analysis.transaction.service.TransactionService;
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
    public List<Document> getList() {
        return transactionService.getList();
    }


}
