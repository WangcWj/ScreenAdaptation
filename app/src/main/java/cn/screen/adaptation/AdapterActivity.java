package cn.screen.adaptation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import cn.screen.adaptation.annotation.IdentificationEnum;
import cn.screen.adaptation.annotation.ScreenAdaptation;
//@ScreenAdaptation(value = IdentificationEnum.HEIGHT)
public class AdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        int densityDpi = getResources().getDisplayMetrics().densityDpi;
        Log.e("WANG","AdapterActivity.onCreate." +densityDpi);
        final View viewById = findViewById(R.id.image);
        viewById.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = viewById.getWidth();
                int height = viewById.getHeight();
                Log.e("WANG","AdapterActivity.onGlobalLayout.getWidth"+width+"   getHeight  " +height );
            }
        });
    }
}
