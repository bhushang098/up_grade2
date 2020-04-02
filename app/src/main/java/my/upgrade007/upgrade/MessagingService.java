package my.upgrade007.upgrade;

import android.app.Notification;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.upgrade.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    public MessagingService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        showNotification(message,title,remoteMessage);

    }

    @Override
    public void onDeletedMessages() {

    }

    public void showNotification(String message,String title,@NonNull RemoteMessage remoteMessage)
    {
        if (remoteMessage.getData().size() > 0) {
            //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            Notification notification = new NotificationCompat.Builder(this, "Messages")
                    .setContentText(message)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.upgrade_logo_final)
                    .build();
            manager.notify(0, notification);
        }
    }
}
