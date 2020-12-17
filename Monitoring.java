package com.example.login1;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.ULocale;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Monitoring extends Service {
    String TAG = "MONITORING";
    Handler handler;
    Runnable test;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference meds = db.collection("medications");

    LocalDate prevDate;
    LocalTime prevTime;
    LocalTime lastExpNoifTime = LocalTime.now().minusMinutes(15);
    LocalTime lastTakenNotifTime = LocalTime.now().minusMinutes(15);
    LocalTime lastConflictNotifTime = LocalTime.now().minusMinutes(15);

    boolean hasNotifExpired = false;
    boolean hasNotifTaken = false;
    boolean hasNotifConflict = false;

    List<String> conflictsList = new ArrayList<String>();

    // method for tick from https://stackoverflow.com/questions/41395537/android-run-a-task-every-4-second
    public Monitoring() {
        handler = new Handler();
        test = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                Log.d(TAG, "Running check");
                prevDate = LocalDate.now();
                prevTime = LocalTime.now();

                // clear conflicts so nothing from last check stays (in case of meds being prescribed / removed in one session)
                if(conflictsList.size() != 0)
                    conflictsList.clear();

                meds.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Map<String, Object> currMed = document.getData();

                                        String id = document.getId();
                                        String name = (String) currMed.get("name");
                                        String conflicts = (String) currMed.get("conflicts");
                                        String expirationDate = (String) currMed.get("expirationDate");
                                        String scheduledTime = (String) currMed.get("scheduledTime");
                                        String hasTaken = (String) currMed.get("hasTaken");
                                        String isExpired = (String) currMed.get("isExpired");
                                        String _prescribed = (String) currMed.get("_prescribed");

                                        // add conflicts for each prescribed medication to array
                                        if(_prescribed.equals("yes") && !conflicts.isEmpty())
                                        {
                                            String[] temp = conflicts.split(",");
                                            conflictsList.addAll(Arrays.asList(temp));

                                            // check for conflicting medications
                                            for (String conf : conflictsList)
                                            {
                                                if(name.equals(conf) && !hasNotifConflict)
                                                {
                                                    sendNotification("Conflicting Medication with "+name, "Remove conflicting medications immediately!");

                                                    lastConflictNotifTime = LocalTime.now();
                                                    hasNotifConflict = true;
                                                }
                                            }
                                        }

                                        // Check if medication is expired, and notify
                                        if(checkExpiration(expirationDate) && _prescribed.equals("yes") && !hasNotifExpired) {
                                            sendNotification("Your medication has expired!",
                                                    "Your prescription of " + name + " has expired! (" + expirationDate + ").  " + "Please open the app and renew it.");

                                            meds.document(id).update("isExpired", "yes");

                                            // keep from notif spamming (only once per day)
                                            lastExpNoifTime = LocalTime.now();
                                            hasNotifExpired = true;
                                        }
                                        else if(!checkExpiration(expirationDate) && _prescribed.equals("yes") && isExpired.equals("yes"))
                                        {
                                            meds.document(id).update("isExpired", "no");
                                        }

                                        // Check if medication has been taken today.  If not, notify.
                                        if(LocalTime.now().isAfter(LocalTime.parse(scheduledTime)) && _prescribed.equals("yes") && hasTaken.equals("no") && !hasNotifTaken)
                                        {
                                            sendNotification("You haven't taken your medication yet!",
                                                    "Your prescription of " + name + " needs to be taken! (" + fixTime(scheduledTime) + ").");

                                            lastTakenNotifTime = LocalTime.now();
                                            hasNotifTaken = true;
                                        }

                                        // if the day has changed since the last tick (10 seconds ago)
                                        // then we must have entered a new day.
                                        if(LocalDate.now().isAfter(prevDate))
                                        {
                                            meds.document(id).update("hasTaken", "no");
                                        }

                                        // if 15 minutes have passed, allow another notification/alert from the app (expiration)
                                        if(Duration.between(LocalTime.now(), lastExpNoifTime).abs().getSeconds() >= (15*58))
                                        {
                                            //reset notifications so they can run again
                                            hasNotifExpired = false;
                                        }

                                        // if 15 minutes have passed, allow another notification/alert from the app (taken med)
                                        if(Duration.between(LocalTime.now(), lastTakenNotifTime).abs().getSeconds() >= (15*56))
                                        {
                                            //reset notifications so they can run again
                                            hasNotifTaken = false;
                                        }

                                        // Reset notification for conflicting meds every 5 minutes (because this is most urgent)
                                        if(Duration.between(LocalTime.now(), lastConflictNotifTime).abs().getSeconds() >= (5*54))
                                        {
                                            //reset notifications so they can run again
                                            hasNotifConflict = false;
                                        }

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

                handler.postDelayed(test, 10000); // every 10 seconds
            }
        };

        handler.postDelayed(test, 0);
    }

    // Make the time string easy to read
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String fixTime(String time)
    {
        LocalTime milTime = LocalTime.parse(time);
        String regTime;

        // show PM instead of AM for noon - 12:59 PM
        if(milTime.isAfter(LocalTime.parse("11:59:59")) && milTime.isBefore(LocalTime.parse("13:00:00")))
        {
            return milTime.toString()+" PM";
        }

        // show 12:00 - 12:59 AM instead of 00:00 AM
        if(milTime.isAfter(LocalTime.parse("00:00:00")) && milTime.isBefore(LocalTime.parse("01:00:00")))
        {
            return milTime.plusHours(12).toString()+" AM";
        }

        // for most cases
        if(milTime.isAfter(LocalTime.parse("12:59:59"))) // noon or after (PM)
        {
            regTime = milTime.minusHours(12).toString()+" PM";
        }
        else // 11:59 or before (AM)
        {
            regTime = milTime.toString()+" AM";
        }

        return regTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkExpiration(String expDate)
    {
        LocalDate expirationDate = LocalDate.parse(expDate);
        return LocalDate.now().isAfter(expirationDate);
    }

    // adapted from code here: https://stackoverflow.com/questions/57827151/attempt-to-invoke-virtual-method-android-app-notification-androidx-core-app-not
    private void sendNotification(String title,String body) {
        // get notification service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // set up intent
        Intent intent = new Intent(this, MenuActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // notification sound
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel=new NotificationChannel("my_notification","n_channel",NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("description");
            notificationChannel.setName("Channel Name");
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // build notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_med)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_med))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setChannelId("my_notification")
                .setColor(Color.parseColor("#3F5996"));

        // execute notification
        assert notificationManager != null;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}