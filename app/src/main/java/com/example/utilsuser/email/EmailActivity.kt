package com.example.utilsuser.email

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.share.email.EmailEntity
import com.example.utilsgather.share.email.EmailSendUtil
import com.example.utilsuser.R
import com.example.utilsuser.coroutine.flow.FlowActivity
import com.example.utilsuser.coroutine.flow_operator.FlowOperatorActivity
import com.example.utilsuser.coroutine.state_flow.SharedFlowActivityClick
import com.example.utilsuser.coroutine.state_flow.StateFlowActivity
import com.example.utilsuser.coroutine.state_flow.StateFlowActivity2
import com.example.utilsuser.coroutine.state_flow.StateFlowActivityClick

class EmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email)

        GuideSettings.set(findViewById(R.id.lv_container),
            arrayOf(
                GuideItemEntity("发送邮件 默认邮箱") {
                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                        setType("message/rfc822")
                        setData(Uri.parse("mailto:"))
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "这是邮件主题")
                        putExtra(Intent.EXTRA_TEXT, "这是邮件内容")
                        setClassName("com.android.email", "com.android.email.activity.ComposeActivityEmailExternal")

                    }
                    startActivity(emailIntent)
                },
                GuideItemEntity("发送邮件 gmail") {
                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                        setType("message/rfc822")
                        setData(Uri.parse("mailto:"))
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "这是邮件主题")
                        putExtra(Intent.EXTRA_TEXT, "这是邮件内容")
//                        setClassName("com.android.email", "com.android.email.activity.ComposeActivityEmailExternal")
                        setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmailExternal")

                    }
                    startActivity(emailIntent)
                },
                GuideItemEntity("发送邮件 复合") {
                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                        setType("message/rfc822")
                        setData(Uri.parse("mailto:"))
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "这是邮件主题")
                        putExtra(Intent.EXTRA_TEXT, "这是邮件内容")
                        setClassName("com.android.email", "com.android.email.activity.ComposeActivityEmailExternal")
                    }
                    val emailIntent_gmail = Intent(Intent.ACTION_SEND).apply {
                        setType("message/rfc822")
                        setData(Uri.parse("mailto:"))
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "这是邮件主题")
                        putExtra(Intent.EXTRA_TEXT, "这是邮件内容")
                        setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmailExternal")
                    }

                    val intentChooser = Intent.createChooser(emailIntent, "选择邮件应用jjj")
                    intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf<Parcelable>(emailIntent_gmail))
                    startActivity(intentChooser)
                },
                GuideItemEntity("发送邮件 复刻了bingo（最基本的）") {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        setData(Uri.parse("mailto:"))
                    }
                    startActivity(emailIntent)
                },
                GuideItemEntity("发送邮件 复刻了bingo（最基本的，但是更精简）") {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        setData(Uri.parse("mailto:"))
                    }
                    startActivity(Intent.createChooser(emailIntent, "请选择 Please Choose"));
                },
                GuideItemEntity("发送邮件 复刻了bingo（传送信息）") {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        setData(Uri.parse("mailto:"))
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com", "hsf@example.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "这是邮件主题")
                        putExtra(Intent.EXTRA_TEXT, "这是邮件内容")

                        putExtra(Intent.EXTRA_CC, arrayOf("csr1@example.com", "csr2@example.com"));
                        putExtra(Intent.EXTRA_BCC, arrayOf("msr1@example.com", "msr2@example.com"));
                    }
                    startActivity(emailIntent)
                },
                GuideItemEntity("发送邮件 复刻了bingo（封装好的）") {
                    val emailEntity = EmailEntity().putRecipientEmail("recipient@example.com", "hsf@example.com")
                        .setEmailSubject("这是邮件主题")
                        .setEmailText("这是邮件内容")
                        .putCcEmail("csr1@example.com")
                        .putBccEmail("msr1@example.com", "msr2@example.com")
                    EmailSendUtil.sendEmail(this@EmailActivity, emailEntity)
                },
                GuideItemEntity("发送邮件 复刻了bingo（封装好的，无抄送人、密送人）") {
                    val emailEntity = EmailEntity().putRecipientEmail("recipient@example.com", "hsf@example.com")
                        .setEmailSubject("这是邮件主题")
                        .setEmailText("这是邮件内容")
                    EmailSendUtil.sendEmail(this@EmailActivity, emailEntity)
                },
                GuideItemEntity("发送邮件 复刻了bingo（封装好的，不填信息）") {
                    EmailSendUtil.sendEmail(this@EmailActivity)
                },
            )
        )
    }
}