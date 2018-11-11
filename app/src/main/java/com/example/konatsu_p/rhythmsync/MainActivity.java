package com.example.konatsu_p.rhythmsync;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private long mCount, mDelay, mPeriod, mCurrentTime;
    private ArrayList<Long> mTapTimeList;
    private Timer mTimer;
    private Handler mHandler;

    private TextView timerTextView, tapTextView;
    private Button startButton, stopButton, tapButton;

    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss.SS", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.timer_text_view);
        tapTextView = (TextView) findViewById(R.id.tap_text_view);

        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        tapButton = (Button) findViewById(R.id.tap_button);

        mHandler = new Handler();

        mCount = 0;
        mDelay = 0;
        mPeriod = 10;
        mCurrentTime = 0;
        mTapTimeList = new ArrayList<>();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount = 0;
                mTapTimeList = new ArrayList<>();
                mTimer = new Timer(false);
                mTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCount++;
                                mCurrentTime = mCount * mPeriod;
                                timerTextView.setText(dataFormat.format(mCurrentTime));
                            }
                        });

                    }
                }, mDelay, mPeriod);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mTimer) {
                    mTimer.cancel();
                    mTimer = null;
                    timerTextView.setText(dataFormat.format(0));
                    tapTextView.setText(dataFormat.format(0));
                }

            }
        });

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTapTimeList.add(mCurrentTime);
                tapTextView.setText(dataFormat.format(mCurrentTime));
                Log.d("TIME：", mTapTimeList.get(mTapTimeList.size() - 1) + "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }


}
