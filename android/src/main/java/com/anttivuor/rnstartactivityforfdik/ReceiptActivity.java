package com.anttivuor.startactivityforfdik;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;

public class ReceiptActivity extends AppCompatActivity {
    Uri callbackData = null;
    Context mContext;
    String payType, approvalType, bizNum, ownerName, storeName, storeAddress, storeTel, memberNum, supplyAmount, vatAmount, totalAmount, cardNum,
            installement, purchaseName, approvalNum, referenceNo, approvalTime, approvalDate, terminalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        CallbackDataProcess();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        CallbackDataProcess();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != callbackData){
            callbackData = null;
        }
        if(null != getIntent()){
            if(null != getIntent().getData()) getIntent().setData(null);
        }else {
            if(null != getParent().getIntent()){
                if(null != getParent().getIntent().getData()) getParent().getIntent().setData(null);
            }
        }
    }


    public void CallbackDataProcess(){
        if(null != getIntent()){

            if(null != getIntent().getData()){
                callbackData = getIntent().getData();
                Intent intent = new Intent(this, RNStartActivityForFdikModule.class);
                intent.putExtra("callbackData", callbackData.toString());
                startActivity(intent);
                finish();
            }
        }
    }
}