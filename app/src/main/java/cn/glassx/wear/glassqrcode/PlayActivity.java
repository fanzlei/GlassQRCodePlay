package cn.glassx.wear.glassqrcode;

/**
 * Created by Fanz on 5/13/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import com.google.zxing.client.glass.CaptureActivity;
import com.squareup.picasso.Picasso;


public class PlayActivity extends Activity {
    private String mediaURL;
    private int mediaType = -1;
    private ImageView btnPlayUrl;
    private SeekBar skbProgress;
    private Player player;
    private SurfaceView surfaceView;
    private boolean initedMedia = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mediaURL = getIntent().getStringExtra(CaptureActivity.RESULT_KEY);
        Log.d("获得URL",mediaURL);
        mediaType = ParseMediaType.parse(mediaURL);
        switch (mediaType){
            case ParseMediaType.TYPE_MUSIC:
                setContentView(R.layout.play_music);
                btnPlayUrl = (ImageView) this.findViewById(R.id.btn_play);
                skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
                skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
                player = new MusicPlayer(skbProgress);
                break;
            case ParseMediaType.TYPE_VIDEO:
                setContentView(R.layout.play_video);
                btnPlayUrl = (ImageView)findViewById(R.id.video_play_icon);
                surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
                skbProgress = (SeekBar) findViewById(R.id.skbProgress);
                skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
                player = new VideoPlayer(surfaceView,skbProgress);
                break;
            case ParseMediaType.TYPE_PICTURE:
                setContentView(R.layout.play_picture);
                ImageView imageView = (ImageView)findViewById(R.id.play_picture);
                Picasso.with(this).load(mediaURL).into(imageView);
                break;
            case ParseMediaType.TYPE_NONE:
                Log.d("PlayActivity","not a supported url");
                setContentView(R.layout.play_none);
        }
    }
/*

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onCenterTap();
        return true;
    }
*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_CENTER:
                onCenterTap();
                break;
            case KeyEvent.KEYCODE_BACK:
                if(player != null){
                    player.stop();
                }
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.seekTo(progress);
        }
    }


    void onCenterTap(){
        switch (mediaType){
            case ParseMediaType.TYPE_PICTURE:

                break;
            case ParseMediaType.TYPE_MUSIC:
                if(player.isPlaying()){
                    Log.d("PlayActivity","暂停播放音乐");
                    btnPlayUrl.setImageResource(R.drawable.icon_play);
                    player.pause();
                }else if(!initedMedia){
                    Log.d("PlayActivity","开始播放音乐");
                    player.playUrl(mediaURL);
                    initedMedia = true;
                    btnPlayUrl.setImageResource(R.drawable.icon_pause);
                }else{
                    Log.d("PlayActivity","播放音乐");
                    player.play();
                    btnPlayUrl.setImageResource(R.drawable.icon_pause);
                }
                break;
            case ParseMediaType.TYPE_VIDEO:
                if(player.isPlaying()){
                    Log.d("PlayActivity","暂停播放视频");
                    btnPlayUrl.setVisibility(View.VISIBLE);
                }else if(!initedMedia){
                    Log.d("PlayActivity","开始播放视频");
                    player.playUrl(mediaURL);
                    btnPlayUrl.setVisibility(View.INVISIBLE);
                }else{
                    Log.d("PlayActivity","播放视频");
                    player.play();
                    btnPlayUrl.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}


