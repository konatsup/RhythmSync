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
    private ArrayList<Long> mTapTimeList1, mTapTimeList2;
    private Timer mTimer;
    private Handler mHandler;

    private TextView timerTextView, tapTextView1, tapTextView2;
    private Button startButton, stopButton, tapButton1, tapButton2;

    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss.SS", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.timer_text_view);
        tapTextView1 = (TextView) findViewById(R.id.tap_text_view1);
        tapTextView2 = (TextView) findViewById(R.id.tap_text_view2);


        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        tapButton1 = (Button) findViewById(R.id.tap_button1);
        tapButton2 = (Button) findViewById(R.id.tap_button2);


        mHandler = new Handler();

        mCount = 0;
        mDelay = 0;
        mPeriod = 10;
        mCurrentTime = 0;
        mTapTimeList1 = new ArrayList<>();
        mTapTimeList2 = new ArrayList<>();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimer == null) {
                    mCount = 0;
                    mTapTimeList1 = new ArrayList<>();
                    mTapTimeList2 = new ArrayList<>();
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
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                    timerTextView.setText(dataFormat.format(0));
                    tapTextView1.setText(dataFormat.format(0));
                    tapTextView2.setText(dataFormat.format(0));
                }

            }
        });

        tapButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTapTimeList1.add(mCurrentTime);
                tapTextView1.setText(dataFormat.format(mCurrentTime));
                Log.d("TIME1：", mTapTimeList1.get(mTapTimeList1.size() - 1) + "");
            }
        });

        tapButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTapTimeList2.add(mCurrentTime);
                tapTextView2.setText(dataFormat.format(mCurrentTime));
                Log.d("TIME2：", mTapTimeList2.get(mTapTimeList2.size() - 1) + "");
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
