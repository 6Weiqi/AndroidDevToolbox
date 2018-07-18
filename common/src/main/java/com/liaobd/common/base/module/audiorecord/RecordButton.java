package com.liaobd.common.base.module.audiorecord;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.liaobd.common.R;
import com.liaobd.common.base.util.FileTool;

import java.io.File;

/**
 * 自定义语音按钮样式
 * (http://blog.csdn.net/zhuyb829/article/details/50071129)
 *
 * @author ss
 * @version V1.0.0
 * @date 2015-7-8 下午4:34:20
 */
@SuppressLint("NewApi")
public class RecordButton extends AppCompatButton {
    private static final int WHAT_MSG_VOLUME_CHANGE = 0x123;
    private static final int WHAT_MSG_RECORDING_STOP = 0x1000;
    private static final int MIN_INTERVAL_TIME = 1 * 1000;// 1s 最短
    public static final int MAX_TIME = 60 * 60 * 1000 + 500;// 60分钟，最长
    private static final int[] RES_VOLUME = {
            R.drawable.bg_mic_recording_0,
            R.drawable.bg_mic_recording_1,
            R.drawable.bg_mic_recording_2,
            R.drawable.bg_mic_recording_3,
    };

    private static ImageView sImageView;
    private boolean mOutputMp3;
    private Context mContext;
    private ExtAudioRecorder mAudioRecorder; //压缩的录音（WAV）
    private OnFinishedRecordListener mFinishedRecordListener;
    private static long sStartTime;
    private Dialog mRecordDialog;
    //    private MediaRecorder recorder;
    private ObtainDecibelThread mThread;
    private File mSaveDir;
    private String mFileName = null;
    private float mTouchY;
    private Handler mVolumeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_MSG_RECORDING_STOP) {
                stopRecording();
                mRecordDialog.dismiss();
            } else if (msg.what == WHAT_MSG_VOLUME_CHANGE) {
                int f = (int) msg.obj;

                Log.e("===>", f + "");

                if (f < 20) {
                    sImageView.setImageResource(RES_VOLUME[0]);
                } else if (f < 30) {
                    sImageView.setImageResource(RES_VOLUME[1]);
                } else if (f < 43) {
                    sImageView.setImageResource(RES_VOLUME[2]);
                } else {
                    sImageView.setImageResource(RES_VOLUME[3]);
                }

            }
        }
    };


    public RecordButton(Context context) {
        super(context);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOutputMp3(boolean outputMp3) {
        mOutputMp3 = outputMp3;
    }

    public void setSaveDir(String dirName) {
        mSaveDir = FileTool.getExternalPrivateMusicDir(mContext, dirName);
    }

    public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
        mFinishedRecordListener = listener;
    }

    @SuppressLint("HandlerLeak")
    private void init(Context ctx) {
        mContext = ctx;
        mSaveDir = FileTool.getExternalPrivateMusicDir(mContext, "rec");
        mRecordDialog = new Dialog(getContext(), R.style.like_toast_dialog_style);
        sImageView = new ImageView(getContext());
        sImageView.setImageResource(RES_VOLUME[3]);
        mRecordDialog.setContentView(sImageView, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mRecordDialog.setOnDismissListener(mDismissListener);
        LayoutParams lp = mRecordDialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
    }

//    private AnimationDrawable anim;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        mTouchY = event.getY();
        if (mTouchY < 0) {
            sImageView.setImageResource(R.drawable.bg_slide_cancel);
//            anim.stop();
        } else {
            sImageView.setImageResource(RES_VOLUME[3]);
//            anim = (AnimationDrawable) sImageView.getBackground();
//            anim.start();
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setText("松开发送");
                initDialogAndStartRecord();
//                anim = (AnimationDrawable) sImageView.getBackground();
//                anim.start();
                break;
            case MotionEvent.ACTION_UP:
                this.setText("按住录音");
                mRecordCountDownTimer.cancel(); // 主动松开时取消计时
                mLast10SecondCountDownTimer.cancel(); // 主动松开时取消计时
                if (mTouchY >= 0 && (System.currentTimeMillis() - sStartTime <= MAX_TIME)) {
                    finishRecord();
                } else if (mTouchY < 0) {  //当手指向上滑，会cancel
                    cancelRecord();
                }
                break;
            case MotionEvent.ACTION_CANCEL: // 异常
                cancelRecord();
                break;
        }

        return true;
    }

    /**
     * 初始化录音对话框 并 开始录音
     */
    private void initDialogAndStartRecord() {
        sStartTime = System.currentTimeMillis();
        startRecording();
        mRecordDialog.show();
    }

    /**
     * 放开手指，结束录音处理
     */
    private void finishRecord() {
        long intervalTime = System.currentTimeMillis() - sStartTime;
        if (intervalTime < MIN_INTERVAL_TIME) {
            mVolumeHandler.sendEmptyMessageDelayed(WHAT_MSG_RECORDING_STOP, 1000);
            sImageView.setImageResource(R.drawable.bg_too_short);
//            anim.stop();
            File file = new File(mFileName);
            file.delete();
            return;
        } else {
            stopRecording();
            mRecordDialog.dismiss();
        }
        //如果有回调，则发送录音结束回调
        if (mFinishedRecordListener != null)
            mFinishedRecordListener.onFinishedRecord(mFileName, intervalTime);
    }

    /**
     * 取消录音对话框和停止录音
     */
    private void cancelRecord() {
        stopRecording();
        mRecordDialog.dismiss();
        //MyToast.makeText(getContext(), "取消录音！", Toast.LENGTH_SHORT);
        File file = new File(mFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 执行录音操作
     */
    private void startRecording() {
        if (mSaveDir == null) {
            return;
        }

        String fileName = new StringBuilder()
                .append("/tmp_sound_")
                .append(System.currentTimeMillis())
                .append(mOutputMp3 ? ".mp3" : ".wav").toString();
        mFileName = new File(mSaveDir, fileName).getPath();
        mAudioRecorder = ExtAudioRecorder.getInstance(false); //未压缩的录音（WAV）
        //设置输出文件
        mAudioRecorder.setOutputFile(mFileName);
        mAudioRecorder.prepare();
        //开始录音
        mAudioRecorder.start();
        mRecordCountDownTimer.start();

        mThread = new ObtainDecibelThread();
        mThread.start();

        //震动提醒
        Vibrator vib = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(100);
    }

    /**
     * 录音开始计时器，允许的最大时长倒数10秒时进入倒计时
     */
    private CountDownTimer mRecordCountDownTimer = new CountDownTimer(MAX_TIME - 500 - 10000, 1000) { // 50秒后开始倒计时
        @Override
        public void onFinish() {
            mLast10SecondCountDownTimer.start();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
    };


    /**
     * 录音最后10秒倒计时器，倒计时结束发送录音
     */
    private CountDownTimer mLast10SecondCountDownTimer = new CountDownTimer(10000, 1000) {
        @Override
        public void onFinish() {
            finishRecord();
        }

        @Override
        public void onTick(long millisUntilFinished) { // 显示倒计时动画
            //            switch ((int)millisUntilFinished / 1000) {
            //                case 10:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_10);
            //                    break;
            //                case 9:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_9);
            //                    break;
            //                case 8:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_8);
            //                    break;
            //                case 7:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_7);
            //                    break;
            //                case 6:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_6);
            //                    break;
            //                case 5:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_5);
            //                    break;
            //                case 4:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_4);
            //                    break;
            //                case 3:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_3);
            //                    break;
            //                case 2:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_2);
            //                    break;
            //                case 1:
            //                    sImageView.setBackgroundResource(R.drawable.mic_count_1);
            //                    break;
            //            }

            Toast.makeText(getContext(), "还有" + ((int) millisUntilFinished / 1000) + "秒结束",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void stopRecording() {
        if (mThread != null) {
            mThread.exit();
            mThread = null;
        }
        /*if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }*/
        if (mAudioRecorder != null) {
            mAudioRecorder.stop();
            mAudioRecorder.release();
            mAudioRecorder = null;
        }
    }

    private class ObtainDecibelThread extends Thread {

        private volatile boolean running = true;

        public void exit() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
//                if (recorder == null || !running) {
//                    continue;
//                }

                if (mAudioRecorder == null) {
                    continue;
                }

//              int x = recorder.getMaxAmplitude(); //振幅
                int x = mAudioRecorder.getMaxAmplitude();//振幅
                if (x != 0 && mTouchY >= 0) {
                    int f = (int) (10 * Math.log(x) / Math.log(10));
                    Message msg = mVolumeHandler.obtainMessage();
                    msg.what = WHAT_MSG_VOLUME_CHANGE;
                    msg.obj = f;
                    mVolumeHandler.sendMessage(msg);
                }

                mVolumeHandler.sendEmptyMessage(-1);
                if (System.currentTimeMillis() - sStartTime > MAX_TIME) {
                    finishRecord();
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private OnDismissListener mDismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            stopRecording();
        }
    };

    public interface OnFinishedRecordListener {
        void onFinishedRecord(String audioPath, long durationMills);
    }

    class CountDown extends CountDownTimer {

        /**
         * @param millisInFuture
         * @param countDownInterval
         */
        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

    }
}