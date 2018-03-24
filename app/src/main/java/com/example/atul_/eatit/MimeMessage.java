package com.example.atul_.eatit;

import android.content.pm.PackageInstaller;

/**
 * Created by Admin on 24-Mar-18.
 */

class MimeMessage {

    InternetAddress sender;
    String subject;
    DataHandler dataHandler;


    public MimeMessage(PackageInstaller.Session session) {
    }

    public void setSender(InternetAddress sender) {
        this.sender = sender;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }
}
