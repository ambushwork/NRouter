package com.netatmo.ylu.nrouter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.netatmo.ylu.annotation.NRouter;

@NRouter(path = "/app/OrderActivity")
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> targetClass = MainActivity$$NRouter.findTargetClass("/app/MainActivity");
                startActivity(new Intent(OrderActivity.this, targetClass));
            }
        });
    }
}
