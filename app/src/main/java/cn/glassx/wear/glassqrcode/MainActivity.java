package cn.glassx.wear.glassqrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.client.glass.CaptureActivity;

import java.io.File;


/**
 * Created by Fanz on 5/14/15.
 */
public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian);

    }

    public void start(View view){
        Intent intent = new Intent(this,PlayActivity.class);
        intent.putExtra(CaptureActivity.RESULT_KEY, "http://www.pocketjourney.com/downloads/pj/video/famous.3gp");
        startActivity(intent);
    }
}
