package qianfeng.a9_3surfaceviewmediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
/*
     SurfaceView+MediaPlayer==>解决ListView中的问题。
     解决资源浪费问题，因为如果你用VideoView的话，每个视频文件都需要一个MediaPlayer，
     其实ListView由始至终只需要一个MediaPlayer就够了。
     所以用SurfaceView+MediaPlayer可以解决ListView中播放视频item而不造成资源浪费的问题。
 */
public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = ((SurfaceView) findViewById(R.id.surfaceView));

        final MediaPlayer mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 异步准备完成后，回调这个方法
                mp.start();
            }
        });
        try {
            // 播放本地视频，没问题
//            mediaPlayer.setDataSource(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"123.mp4").getAbsolutePath());
            // 播放网络视频，可能有延迟
            mediaPlayer.setDataSource("http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 当surface创建成功后，代表视频文件已经加载好了，可以进行播放了,在这里进行MediaPlayer的异步准备，否则如果MediaPlayer异步准备完成而SurfaceView尚未初始化，会有异常。
                //设置显示视频画面的SurfaceHolder
                mediaPlayer.setDisplay(holder); // 核心代码哦！
                mediaPlayer.prepareAsync();

                // seekbar的拖动其实和之前做MediaPlayer一样？

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
}
