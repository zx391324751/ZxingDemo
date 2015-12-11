package com.best.android.zxinglib;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.best.android.zxinglibrary.activity.CaptureActivity;
import com.best.android.zxinglibrary.encoding.EncodingHandler;
import com.google.zxing.WriterException;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private EditText etData;
    private ImageView ivQRcode;

    private final int widthAndHeight = 1000; //二维码图片宽和高

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        findViewById(R.id.activity_main_btn_scan).setOnClickListener(onClickListener);
        findViewById(R.id.activity_main_btn_produce).setOnClickListener(onClickListener);

        tvResult = (TextView)findViewById(R.id.activity_main_tv_result);
        etData = (EditText)findViewById(R.id.activity_main_et_data);
        ivQRcode = (ImageView)findViewById(R.id.activity_main_iv_QRcode);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.activity_main_btn_scan:
                    startScan();
                    break;
                case R.id.activity_main_btn_produce:
                    createQRcode();
                    break;
            }
        }
    };

    //生成二维码图片
    private void createQRcode() {
        if(etData.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "请输入数据", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Bitmap QRBitmap = EncodingHandler.createQRCode(etData.getText().toString(), widthAndHeight);
            if(QRBitmap != null)
                ivQRcode.setImageBitmap(QRBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    //扫描二维码
    private void startScan() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String scanResult = data.getStringExtra(CaptureActivity.SCAN_RESULT_KEY);
            tvResult.setText(scanResult);
        }
    }
}
