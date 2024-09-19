package com.example.utilsgather.share.email;

public class EmailEntity {
    // 收件人email
    private String[] recipientEmail;
    // 邮件主题
    private String emailSubject;
    // 邮件正文
    private String emailText;
    // 抄送人email
    private String[] ccEmail;
    // 密送人email
    private String[] bccEmail;


    public EmailEntity putRecipientEmail(String... recipientEmail) {
        this.recipientEmail = recipientEmail;
        return this;
    }

    public String[] getRecipientEmail() {
        return recipientEmail;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public EmailEntity setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
        return this;
    }
    public String getEmailText() {
        return emailText;
    }

    public EmailEntity setEmailText(String emailText) {
        this.emailText = emailText;
        return this;
    }

    public String[] getCcEmail() {
        return ccEmail;
    }

    public EmailEntity putCcEmail(String... ccEmail) {
        this.ccEmail = ccEmail;
        return this;
    }

    public String[] getBccEmail() {
        return bccEmail;
    }

    public EmailEntity putBccEmail(String... bccEmail) {
        this.bccEmail = bccEmail;
        return this;
    }
}
