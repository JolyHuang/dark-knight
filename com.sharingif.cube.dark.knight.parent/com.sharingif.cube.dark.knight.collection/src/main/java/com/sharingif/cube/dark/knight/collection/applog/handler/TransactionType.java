package com.sharingif.cube.dark.knight.collection.applog.handler;

/**
 * 交易类型
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/26 下午5:11
 */
public enum TransactionType {

    TRANS_TYPE("transType")
    ,SERVER_START("serverStart")
    ,SERVER_STOP("serverStop")
    ,TRANSACTION_BEGIN("transactionBegin")
    ,TRANSACTION_INFO("transactionInfo")
    ,TRANSACTION_END("transactionEnd")
    ,TRANSACTION_ERROR("transactionError")
    ,CONTROLLER_BEGIN("controllerBegin")
    ,CONTROLLER_END("controllerEnd")
    ,CONTROLLER_ERROR("controllerError")
    ,TRANSPORT_BEGIN("transportBegin")
    ,TRANSPORT_END("transportEnd")
    ,TRANSPORT_ERROR("transportError");

    private String value;
    TransactionType(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value;
    }

}
