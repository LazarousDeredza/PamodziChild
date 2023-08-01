package com.pamodzichild.SendEmail;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTask extends AsyncTask<Void, Void, Boolean> {

    private String mRecipient;
    private String mSubject;
    private String mMessage;
    private Context mContext;

    private String textType;

    public SendMailTask(String recipient, String subject, String message) {
        mRecipient = recipient;
        mSubject = subject;
        mMessage = message;
    }

    public SendMailTask(String mRecipient, String mSubject, String mMessage, Context mContext) {
        this.mRecipient = mRecipient;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
        this.mContext = mContext;
    }

    public SendMailTask(String mRecipient, String mSubject, String mMessage, Context mContext, String textType) {
        this.mRecipient = mRecipient;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
        this.mContext = mContext;
        this.textType = textType;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            String host = "smtp.gmail.com";
            String username = "pamodzichildafrica@gmail.com";
            String password ="imkgbnipyvqzczwh";
            String port = "465";

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", port);

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mRecipient));
            message.setSubject(mSubject);
            message.setContent(mMessage, "text/html");

            Transport.send(message);




        } catch (MessagingException e) {
            e.printStackTrace();

            Log.d("SendMailTask", "Error Sending Mail"+e.getMessage());
        }
        return true;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            // If the email was sent successfully, display a toast notification
         //   Toast.makeText(mContext, "Email sent successfully", Toast.LENGTH_SHORT).show();
            Log.d("SendMailTask", "Mail Sent Successfully");

        } else {
            // If there was an error sending the email, display an error message
           // Toast.makeText(mContext, "Error sending email", Toast.LENGTH_SHORT).show();
            Log.d("SendMailTask", "Error Sending Mail");
        }
    }
}