package com.example.doctromtinnhan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    private static String Sms="android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Sms)) {
            Bundle bundle = intent.getExtras();
            Object[] arrPdus = (Object[]) bundle.get("pdus");
            for (int i = 0; i < arrPdus.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) arrPdus[i]);
                String noidung = smsMessage.getMessageBody();
                String phone = smsMessage.getOriginatingAddress();
                long time = smsMessage.getTimestampMillis();
                Date date = new Date(time);
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
                String dateFormated = dateFormat.format(date);

                Toast.makeText(context, "Số điện thoại:" + phone + "\nNội dung:" + noidung + "\nThời gian:" + dateFormated, Toast.LENGTH_LONG).show();
            }
        }
    }
}
