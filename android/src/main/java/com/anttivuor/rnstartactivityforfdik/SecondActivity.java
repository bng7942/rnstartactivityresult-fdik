package com.anttivuor.startactivityforfdik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);

    findViewById(R.id.result_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent result = new Intent();
        result.putExtra("RESULT", "SUCCESS");
        setResult(Activity.RESULT_OK, result);
        finish();
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}