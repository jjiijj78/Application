package b1g4.com.yourseat;

public class PushNotification {


//        /*
//         * 푸시 알림
//         * NotifacationManager 이용
//         * */
//        CharSequence title = getString(R.string.pushTitle);
//        CharSequence description = getString(R.string.pushDescription);
//
//        // 누가버전 이하 노티처리
//        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1){
//            Log.d("PUSH NOTI: VersionCheck", "누가버전이하");
//            Intent intent = new Intent();
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher);
//            Bitmap bitmap = bitmapDrawable.getBitmap();
//            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext()).
//                    setLargeIcon(bitmap)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setWhen(System.currentTimeMillis()).
//                            setShowWhen(true).
//                            setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_MAX)
//                    .setContentTitle(title)
//                    .setContentText(description)
//                    .setDefaults(Notification.DEFAULT_VIBRATE)
//                    .setFullScreenIntent(pendingIntent,true)
//                    .setContentIntent(pendingIntent);
//
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0,builder.build());
//
//        }
//        // 오레오 이상 노티처리 - 오레오 버전부터 노티를 처리하려면 채널이 존재해야합니다.
//        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            Log.d("PUSH NOTI: VersionCheck", "오레오이상");
////                    BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher);
////                    Bitmap bitmap = bitmapDrawable.getBitmap();
//
//            Intent intent = new Intent();
//            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            String Noti_Channel_ID = "Noti";
//            String Noti_Channel_Group_ID = "Noti_Group";
//
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationChannel notificationChannel = new NotificationChannel(Noti_Channel_ID,Noti_Channel_Group_ID,importance);
//
////                    notificationManager.deleteNotificationChannel("testid"); 채널삭제
//
//            /**
//             * 채널이 있는지 체크해서 없을경우 만들고 있으면 채널을 재사용합니다.
//             */
//            if(notificationManager.getNotificationChannel(Noti_Channel_ID) != null){
//                Log.d("PUSH NOTI: ChannelCheck", "채널이 이미 존재합니다.");
//            }
//            else{
//                Log.d("PUSH NOTI: ChannelCheck", "채널이 존재하지 않습니다.");
//                notificationManager.createNotificationChannel(notificationChannel);
//            }
//
//            notificationManager.createNotificationChannel(notificationChannel);
////                    Log.e("로그확인","===="+notificationManager.getNotificationChannel("testid1"));
////                    notificationManager.getNotificationChannel("testid");
//
//
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),Noti_Channel_ID)
//                    .setLargeIcon(null).setSmallIcon(R.mipmap.ic_launcher)
//                    .setWhen(System.currentTimeMillis()).setShowWhen(true).
//                            setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_MAX)
//                    .setContentTitle(title)
//                    .setContentText(description)
//                    .setFullScreenIntent(pendingIntent, true)
//                    .setContentIntent(pendingIntent)
//                    .addAction(android.R.drawable.star_on, "앉았다!", pendingIntent)
//                    .addAction(android.R.drawable.star_off, "못 앉았다,,", pendingIntent);
//
////                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0,builder.build());
//        }
}
