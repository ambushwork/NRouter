package com.netatmo.ylu.nrouter;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.netatmo.ylu.annotation.NRouter;

@NRouter(path = "/app/PersonActivity")
public class PersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> targetClass = OrderActivity$$NRouter.findTargetClass("/app/OrderActivity");
                startActivity(new Intent(PersonActivity.this, targetClass));
            }
        });
    }
}
