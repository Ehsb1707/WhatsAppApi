public class MessageWhatsapp {
    public static final int UN_SENT = 0 ;
    public static final int DELIVERED = 1 ;
    public static final int ERROR_STATUS_INT = 2 ;
    public static final int SENT_STATUS_INT = 3 ;
    public static final int ACCEPTED_STATUS_INT = 4 ;
    public static final int STATUS_SEEN_INT = 5 ;
    public static final String ACCEPTED_STATUS = "Status message---> ✔✔ - Sent & Received";
    public static final String SEEN_STATUS = "Status Message ----> ✔✔ Sent & Accepted & Read";
    public static final String ERROR_STATUS = "Status Message ----> Error ! The number doesn't exists on whatsapp";
    public static final String DELIVERED_STATUS = "Status Message ----> delivered ";
    public static final String STATUS_SENT = "Status Message ----> ✔ - Sent" ;
    private final String phoneNumber ;
    private final String message ;
    private int sentType ;
    private String status ;
    private String response ;


    // Constructor ;
    public MessageWhatsapp (String phoneNumber , String message ) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.status = "Status Message--->  Not sent yet. Loading...";
        this.response = null ;
        this.sentType = UN_SENT ;
    }

    public void setSentType (int sentType) {
        if (sentType >= 0 && sentType <= STATUS_SEEN_INT) {
            this.sentType = sentType ;
            setStatus(sentType) ;
        }
    }

    public void setStatus (int status) {
        switch (status) {
            case DELIVERED ->  this.status = DELIVERED_STATUS ;
            case SENT_STATUS_INT ->  this.status = STATUS_SENT;
            case ACCEPTED_STATUS_INT ->  this.status = ACCEPTED_STATUS ;
            case STATUS_SEEN_INT ->  this.status = ERROR_STATUS ;
        }
    }

    public int getSentType () { return sentType ;}

    public void setResponse (String response ) { this.response = response ;}

    public String getResponse() { return response; }

    public String getStatus () { return this.status ;}

    public String getFormatPhoneNumber () { return this.phoneNumber ;}

    public String getPhoneNumber () { return  this.phoneNumber.substring(1) ; }

    public String getMessage() { return this.message ;}

    public boolean isSent () { return this.sentType == DELIVERED ;}

    @Override
    public String toString() {
        return "MessageWhatsapp{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                ", sentType=" + sentType +
                ", status='" + status + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
