package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView timercount;
    SeekBar seekbar;
    Boolean counterisActive = false;
    CountDownTimer countDownTimer;
    Button goButton;

    public void resetTimer() {
        timercount.setText("0:30");
        seekbar.setProgress(30);
        seekbar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO");
        counterisActive =false;
    }

    public void timerbtn(View view) {
        if(counterisActive) {
            resetTimer();
        }else {
            counterisActive=true;
            seekbar.setEnabled(false);
            goButton.setText("STOP!!");

            countDownTimer = new CountDownTimer(seekbar.getProgress() * 1000 + 100, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerLeft((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    MediaPlayer.create(getApplicationContext(), R.raw.demomusic);
                    mediaPlayer.start();
                    resetTimer();
                    Toast.makeText(getApplicationContext(), "Timer-Ended", Toast.LENGTH_SHORT).show();

                }
            }.start();
        }
    }

    public void timerLeft(int progress) {
        int min = progress/60;
        int second =progress - (min*60);
        String secondString = Integer.toString(second);
        if(second < 9 ) {
            secondString = "0" + secondString;
        }
        timercount.setText(Integer.toString(min) + " : " + secondString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = findViewById(R.id.countdown_seekBar);
        timercount = findViewById(R.id.timer_count);
        goButton = findViewById(R.id.go_btn);

        seekbar.setMax(600);
        seekbar.setProgress(30);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               timerLeft(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}