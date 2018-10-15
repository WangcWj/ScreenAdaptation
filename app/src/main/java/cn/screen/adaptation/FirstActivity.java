package cn.screen.adaptation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import cn.screen.adaptation.annotation.IdentificationEnum;
import cn.screen.adaptation.annotation.ScreenAdaptation;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.cancelAdaptScreen(getApplication(),this);
        setContentView(R.layout.activity_first);

        final View viewById = findViewById(R.id.image);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,AdapterActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float densityDpi = getResources().getDisplayMetrics().scaledDensity;
                Log.e("WANG","FirstActivity.onCreate." +densityDpi);
                View inflate = LayoutInflater.from(FirstActivity.this).inflate(R.layout.dialog_layput, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FirstActivity.this)
                        .setView(inflate);
                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.show();
            }
        });
    }
}
