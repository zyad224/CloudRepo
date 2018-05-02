package Team07.com.user;

public class Transaction {
    private int transactionId;
    private int transactionPeanuts;
    private String transactionSender;
    private String transactionReceiver;
    private String transactionContent;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionPeanuts() {
        return transactionPeanuts;
    }

    public void setTransactionPeanuts(int transactionPeanuts) {
        this.transactionPeanuts = transactionPeanuts;
    }

    public String getTransactionSender() {
        return transactionSender;
    }

    public void setTransactionSender(String transactionSender) {
        this.transactionSender = transactionSender;
    }

    public String getTransactionReceiver() {
        return transactionReceiver;
    }

    public void setTransactionReceiver(String transactionReceiver) {
        this.transactionReceiver = transactionReceiver;
    }

    public String getTransactionContent() {
        return transactionContent;
    }

    public void setTransactionContent(String transactionContent) {
        this.transactionContent = transactionContent;
    }
}
