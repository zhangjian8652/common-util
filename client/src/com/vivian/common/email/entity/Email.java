package com.vivian.common.email.entity;

/**
 * @Author zhangjian
 * @Date 2015/10/15
 */
public class Email {
    private String from;
    private String to;
    private String subject;
    private String attachment;
    private String context;
    private String[] ccAddress;
    private String[] bcAddress;


    public Email() {
    }

    public Email(String from, String to, String subject,String attachment) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.attachment = attachment;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "Email{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", attachment='" + attachment + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
