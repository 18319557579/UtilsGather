package com.example.utilsgather.share.email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

public class EmailSendUtil {
    /**
     * 发送邮件
     */
    public static void sendEmail(Context context, @Nullable EmailEntity emailEntity) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));

        if (emailEntity != null) {
            String[] recipientEmail = emailEntity.getRecipientEmail();
            if (recipientEmail != null)
                emailIntent.putExtra(Intent.EXTRA_EMAIL, recipientEmail);

            String emailSubject = emailEntity.getEmailSubject();
            if (emailSubject != null)
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);

            String emailText = emailEntity.getEmailText();
            if (emailText != null)
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);

            String[] ccEmail = emailEntity.getCcEmail();
            if (ccEmail != null)
                emailIntent.putExtra(Intent.EXTRA_CC, ccEmail);

            String[] bccEmail = emailEntity.getBccEmail();
            if (bccEmail != null)
                emailIntent.putExtra(Intent.EXTRA_BCC, bccEmail);
        }

        context.startActivity(emailIntent);
    }

    /**
     * 仅仅是打开邮件发送界面，不携带任何信息
     */
    public static void sendEmail(Context context) {
        sendEmail(context, null);
    }
}
