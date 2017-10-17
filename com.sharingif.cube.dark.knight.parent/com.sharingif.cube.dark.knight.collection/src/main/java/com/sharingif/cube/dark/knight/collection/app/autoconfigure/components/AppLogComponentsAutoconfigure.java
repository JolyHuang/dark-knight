package com.sharingif.cube.dark.knight.collection.app.autoconfigure.components;

import com.sharingif.cube.dark.knight.collection.applog.handler.AppLogDataHandler;
import com.sharingif.cube.dark.knight.collection.applog.handler.TransactionType;
import com.sharingif.cube.dark.knight.collection.applog.write.MongodbDataWrite;
import com.sharingif.cube.dark.knight.collection.file.handler.CompositeDataHandler;
import com.sharingif.cube.dark.knight.collection.file.handler.DataHandler;
import com.sharingif.cube.dark.knight.collection.file.handler.GroupData;
import com.sharingif.cube.dark.knight.collection.file.write.DataWrite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * app日志处理
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/16 下午5:29
 */
@Configuration
public class AppLogComponentsAutoconfigure {

    protected GroupData[] getTransactionBeginGroupData() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
                ,new GroupData(2,"userId")
                ,new GroupData(3,"thread")
                ,new GroupData(4,"transUniqueId")
                ,new GroupData(6,"threadId")
                ,new GroupData(7,"method")
                ,new GroupData(8,"transId")
        };

        return groupIndexArray;
    }

    protected Pattern getTransactionBeginFindPattern() {
        return Pattern.compile("(.{23}) \\[(.*)\\] \\[(.*)\\] INFO  \\[(.*)\\] (.*)thdId:(.*), method:(.*), trsId:(.*)");
    }

    protected GroupData[] getTransactionEndGroupData() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
                ,new GroupData(2,"userId")
                ,new GroupData(3,"thread")
                ,new GroupData(4,"transUniqueId")
                ,new GroupData(6,"threadId")
                ,new GroupData(7,"method")
                ,new GroupData(8,"transId")
                ,new GroupData(9,"transExcuteTime")
        };

        return groupIndexArray;
    }

    protected Pattern getTransactionEndFindPattern() {
        return Pattern.compile("(.{23}) \\[(.*)\\] \\[(.*)\\] INFO  \\[(.*)\\] (.*)thdId:(.*), method:(.*), trsId:(.*), exTime:(.*)");
    }

    protected GroupData[] getTransactionErrorGroupData() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
                ,new GroupData(2,"userId")
                ,new GroupData(3,"thread")
                ,new GroupData(4,"transUniqueId")
                ,new GroupData(6,"threadId")
                ,new GroupData(7,"method")
                ,new GroupData(8,"transId")
                ,new GroupData(9,"transExcuteTime")
                ,new GroupData(10,"message")
                ,new GroupData(11,"localizedMessage")
                ,new GroupData(12,"errorCause")
        };

        return groupIndexArray;
    }

    protected Pattern getTransactionErrorFindPattern() {
        return Pattern.compile("(.{23}) \\[(.*)\\] \\[(.*)\\] ERROR \\[(.*)\\] (.*)thdId:(.*), method:(.*), trsId:(.*), exTime:(.*), message:(.*), localizedMessage:(.*), errorCause:([\\s\\S]*)");
    }

    @Bean("startedBootstrapDataHandler")
    public AppLogDataHandler createStartedBootstrapDataHandler() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
                ,new GroupData(3,"takesTime")
        };

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.SERVER_START.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)Started Bootstrap(.*)"));
        appLogDataHandler.setFindPattern(Pattern.compile("(.{23})(.*)Started Bootstrap in (.*) seconds (.*)"));
        appLogDataHandler.setGroupIndexArray(groupIndexArray);

        return appLogDataHandler;
    }

    @Bean("beansOnShutdownDataHandler")
    public AppLogDataHandler createBeansOnShutdownDataHandler() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
        };

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.SERVER_STOP.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)beans on shutdown"));
        appLogDataHandler.setFindPattern(Pattern.compile("(.{23})(.*)"));
        appLogDataHandler.setGroupIndexArray(groupIndexArray);

        return appLogDataHandler;

    }

    @Bean("transactionBeginDataHandler")
    public AppLogDataHandler createTransactionBeginDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.TRANSACTION_BEGIN.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)transaction begin===>(.*)"));
        appLogDataHandler.setFindPattern(getTransactionBeginFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionBeginGroupData());

        return appLogDataHandler;
    }

    @Bean("transactionEndDataHandler")
    public AppLogDataHandler createTransactionEndDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.TRANSACTION_END.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)transaction end===>(.*)"));
        appLogDataHandler.setFindPattern(getTransactionEndFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionEndGroupData());

        return appLogDataHandler;
    }

    @Bean("transactionInfoDataHandler")
    public AppLogDataHandler TransactionInfoDataHandler() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
                ,new GroupData(2,"userId")
                ,new GroupData(3,"thread")
                ,new GroupData(4,"transUniqueId")
                ,new GroupData(6,"info")
        };

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.TRANSACTION_INFO.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*) \\[(.*)\\] INFO  \\[(.*)\\] (.*)"));
        appLogDataHandler.setFindPattern(Pattern.compile("(.{23}) \\[(.*)\\] \\[(.*)\\] INFO  \\[(.*)\\] (.*) - (.*)"));
        appLogDataHandler.setGroupIndexArray(groupIndexArray);

        return appLogDataHandler;
    }

    @Bean("transactionErrorDataHandler")
    public AppLogDataHandler createTransactionErrorDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.TRANSACTION_ERROR.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)transaction error===>([\\s\\S]*)"));
        appLogDataHandler.setFindPattern(getTransactionErrorFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionErrorGroupData());

        return appLogDataHandler;
    }

    @Bean("controllerBeginDataHandler")
    public AppLogDataHandler createControllerBeginDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.CONTROLLER_BEGIN.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)controller begin===>(.*)"));
        appLogDataHandler.setFindPattern(getTransactionBeginFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionBeginGroupData());

        return appLogDataHandler;
    }

    @Bean("controllerEndDataHandler")
    public AppLogDataHandler createControllerEndDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.CONTROLLER_END.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)controller end===>(.*)"));
        appLogDataHandler.setFindPattern(getTransactionEndFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionEndGroupData());

        return appLogDataHandler;
    }

    @Bean("controllerErrorDataHandler")
    public AppLogDataHandler createControllerErrorDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.CONTROLLER_ERROR.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)controller error===>([\\s\\S]*)"));
        appLogDataHandler.setFindPattern(getTransactionErrorFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionErrorGroupData());

        return appLogDataHandler;
    }

    @Bean("transportBeginDataHandler")
    public AppLogDataHandler createTransportBeginDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.TRANSPORT_BEGIN.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)Transport begin===>(.*)"));
        appLogDataHandler.setFindPattern(getTransactionBeginFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionBeginGroupData());

        return appLogDataHandler;
    }

    @Bean("transportEndDataHandler")
    public AppLogDataHandler createTransportEndDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.TRANSPORT_END.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)Transport end===>(.*)"));
        appLogDataHandler.setFindPattern(getTransactionEndFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionEndGroupData());

        return appLogDataHandler;
    }

    @Bean("transportErrorDataHandler")
    public AppLogDataHandler createTransportErrorDataHandler() {

        AppLogDataHandler appLogDataHandler = new AppLogDataHandler();
        appLogDataHandler.setType(TransactionType.TRANSPORT_ERROR.toString());
        appLogDataHandler.setMathPattern(Pattern.compile("(.*)Transport error===>([\\s\\S]*)"));
        appLogDataHandler.setFindPattern(getTransactionErrorFindPattern());
        appLogDataHandler.setGroupIndexArray(getTransactionErrorGroupData());

        return appLogDataHandler;
    }

    @Bean("appInfoCompositeDataHandler")
    public CompositeDataHandler createAppInfoCompositeDataHandler(
            AppLogDataHandler startedBootstrapDataHandler,
            AppLogDataHandler beansOnShutdownDataHandler,
            AppLogDataHandler transactionBeginDataHandler,
            AppLogDataHandler transactionEndDataHandler,
            AppLogDataHandler transactionErrorDataHandler,
            AppLogDataHandler controllerBeginDataHandler,
            AppLogDataHandler controllerEndDataHandler,
            AppLogDataHandler controllerErrorDataHandler,
            AppLogDataHandler transportBeginDataHandler,
            AppLogDataHandler transportEndDataHandler,
            AppLogDataHandler transportErrorDataHandler,
            AppLogDataHandler transactionInfoDataHandler,

            MongodbDataWrite mongodbDataWrite
    ) {

        List<DataHandler> dataHandlerList = new ArrayList<DataHandler>();
        dataHandlerList.add(startedBootstrapDataHandler);
        dataHandlerList.add(beansOnShutdownDataHandler);
        dataHandlerList.add(transactionBeginDataHandler);
        dataHandlerList.add(transactionEndDataHandler);
        dataHandlerList.add(transactionErrorDataHandler);
        dataHandlerList.add(controllerBeginDataHandler);
        dataHandlerList.add(controllerEndDataHandler);
        dataHandlerList.add(controllerErrorDataHandler);
        dataHandlerList.add(transportBeginDataHandler);
        dataHandlerList.add(transportEndDataHandler);
        dataHandlerList.add(transportErrorDataHandler);
        dataHandlerList.add(transactionInfoDataHandler);


        List<DataWrite> dataWriteList  = new  ArrayList<DataWrite>();
        dataWriteList.add(mongodbDataWrite);

        CompositeDataHandler compositeDataHandler = new CompositeDataHandler();
        compositeDataHandler.setDataHandlerList(dataHandlerList);
        compositeDataHandler.setDataWriteList(dataWriteList);

        return compositeDataHandler;
    }

}
