package com.example.job.Reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.job.MainActivity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.job.R;

public class ReminderBroadcastReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "100";
    private static final int NOTIFICATION_ID = 123;
    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 在这里处理定时器触发的操作
        Toast.makeText(context, (String)intent.getExtras().get("content"), Toast.LENGTH_SHORT).show();
        System.out.println("fuck");

        // 示例：手机震动
        playMusic(context);
        vibrateDevice(context);
        Intent stopMusicIntent = new Intent(MainActivity.STOP_MUSIC_ACTION);
        context.sendBroadcast(stopMusicIntent);
    }

    private void vibrateDevice(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // 判断设备是否支持震动
        if (vibrator != null && vibrator.hasVibrator()) {
            // Android 版本兼容性判断
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(2000);
            }
        }
    }
    private void playMusic(Context context) {
        // 初始化 MediaPlayer
        mediaPlayer = MediaPlayer.create(context, R.raw.one_five); // 替换为你的音乐文件名

        // 播放音乐
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
