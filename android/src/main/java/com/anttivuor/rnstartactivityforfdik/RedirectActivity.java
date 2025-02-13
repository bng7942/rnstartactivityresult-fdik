package com.anttivuor.startactivityforfdik;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RedirectActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_redirect);
    RNStartActivityForFdikModule.mPromise.resolve("returnReact");
    RNStartActivityForFdikModule.mPromise = null;;
    // Intent intent = new Intent(this, RNStartActivityForFdikModule.class);
    // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
    // startActivityForResult(intent, 100);
    finish();
  }
}