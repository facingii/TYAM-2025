package mx.uv.fiee.iinf.tyam.foregroundserviceapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {
    private static final String TAG = "MarJul";
    public static final String CHANNEL_ID = "MyServiceChannel";
    public static final String CHANNEL_DESC = "Foreground Service Notifications";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent (this, MainActivity.class);
        notificationIntent.putExtra ("GETTINGDATAFROMSERVICE", "VALUEFROMSERVICE");
        notificationIntent.setAction ("GETTINGDATAFROMSERVICE");
        PendingIntent pendingIntent = PendingIntent.getActivity (this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // a partir de la versión 8 API 26, las notificaciones del sistema requieren un canal
        // de modo que es posible tener multiples canales de notificación, cada uno destinado a objetivo determinado
        // con configuraciones específicas
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel (CHANNEL_ID, CHANNEL_DESC, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights (false);
            channel.setShowBadge (true);
            NotificationManager manager = (NotificationManager) getSystemService (NOTIFICATION_SERVICE);
            manager.createNotificationChannel (channel);
        }

        // creamos una imagen de mapa de bits a partir del archivo de recursos, para ser usado como ícono
        Bitmap icon = BitmapFactory.decodeResource (getResources (), R.mipmap.ic_launcher_round);

        // la clave Notification.Builder requiere mínimo API 26 para ser ejecutado, como el proyecto se dirige al API 23 como
        // SDK mínimo, es necesario recurrir a la librería de compatibilidad para asegurar que la notificación se cree correctamente
        // en versiones previas a android 8
        //Notification notification = new Notification.Builder (getBaseContext (), "NOTICHANNELTYAM")
        Notification notification = new NotificationCompat.Builder (this, CHANNEL_ID)
                .setContentTitle ("Foreground Service Running")
                .setContentText ("Operation running please wait")
                .setSmallIcon (R.mipmap.ic_launcher_round)
                .setContentIntent (pendingIntent)
                .setLargeIcon (icon)
                .build ();

        startForeground (100001, notification); // se establece la bandera para servicio en primer plano y la notificación

        // es necesario ejecutar la tarea de modo asíncrono de modo que su ejecución no bloquee
        // la finalización del método
        new Thread ( () -> {
            try {
                Thread.sleep (20000);
                Log.i (TAG, "Foreground service running");
            } catch (InterruptedException ex) {  ex.printStackTrace (); }
            stopSelf (); // al finalizar la tarea se detiene el servicio
        } ).start ();

        return START_STICKY;
    }

}