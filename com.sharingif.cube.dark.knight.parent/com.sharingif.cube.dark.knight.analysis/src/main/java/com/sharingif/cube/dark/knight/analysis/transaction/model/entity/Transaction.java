package com.sharingif.cube.dark.knight.analysis.transaction.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Transaction
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/30 下午5:46
 */
public class Transaction {

    public static final String TRANSACTION_BEGIN = "transactionBegin";

    public static final String TRANS_TYPE_KEY = "transType";
    public static final String TRANS_ID_KEY = "transId";
    public static final String MESSAGE_KEY = "message";
    public static final String START_TIME_KEY = "startTime";
    public static final String TRANS_UNIQUE_ID_KEY = "transUniqueId";

    private String transType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTimeBegin;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTimeEnd;
    private String userId;
    private String thread;
    private String transUniqueId;
    private String threadId;
    private String method;
    private String transId;
    private String transExcuteTime;
    private String message;
    private String localizedMessage;

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Date getStartTimeBegin() {
        return startTimeBegin;
    }

    public void setStartTimeBegin(Date startTimeBegin) {
        this.startTimeBegin = startTimeBegin;
    }

    public Date getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(Date startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getTransUniqueId() {
        return transUniqueId;
    }

    public void setTransUniqueId(String transUniqueId) {
        this.transUniqueId = transUniqueId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTransExcuteTime() {
        return transExcuteTime;
    }

    public void setTransExcuteTime(String transExcuteTime) {
        this.transExcuteTime = transExcuteTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("transType='").append(transType).append('\'');
        sb.append(", startTimeBegin=").append(startTimeBegin);
        sb.append(", startTimeEnd=").append(startTimeEnd);
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", thread='").append(thread).append('\'');
        sb.append(", transUniqueId='").append(transUniqueId).append('\'');
        sb.append(", threadId='").append(threadId).append('\'');
        sb.append(", method='").append(method).append('\'');
        sb.append(", transId='").append(transId).append('\'');
        sb.append(", transExcuteTime='").append(transExcuteTime).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", localizedMessage='").append(localizedMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
