package com.anttivuor.startactivityforfdik;

import android.app.Activity;
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
        setContentView(R.layout.activity_receipt);
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
                ((TextView)findViewById(R.id.txt_receipt)).setText(showReceipData());
                // Intent intent = new Intent();
                // intent.putExtra("callbackData", callbackData.toString());
                // setResult(Activity.RESULT_OK, intent);

                // finish();
            }
        }
    }

    public String showReceipData(){
        StringBuilder strResult = new StringBuilder();
        Log.e("urlLog",callbackData.toString());
//        String payType, bizNum, storeName, storeAddress, storeTel, memberNum, supplyAmount, vatAmount, totalAmount, cardNum,
//                purchaseName, approvalNum, referenceNo, approvalTime, approvalDate, terminalNum;
        payType = callbackData.getQueryParameter("cardCashSe");
        strResult.append("cardCashSe : "    + callbackData.getQueryParameter("cardCashSe") + "\n");
        strResult.append("delngSe : "       + callbackData.getQueryParameter("delngSe") + "\n");
        approvalType = callbackData.getQueryParameter("delngSe");
        String setleSuccesAt = callbackData.getQueryParameter("setleSuccesAt");
        strResult.append("setleSuccesAt : " + setleSuccesAt + "\n");
        if("O".equals(setleSuccesAt)){
            strResult.append("setleMessage : "  + callbackData.getQueryParameter("setleMessage") + "\n");
            strResult.append("confmNo : "       + callbackData.getQueryParameter("confmNo") + "\n");
            approvalNum = callbackData.getQueryParameter("confmNo");
            strResult.append("confmDe : "       + callbackData.getQueryParameter("confmDe") + "\n");
            approvalDate = callbackData.getQueryParameter("confmDe");
            strResult.append("confmTime : "     + callbackData.getQueryParameter("confmTime") + "\n");
            approvalTime = callbackData.getQueryParameter("confmTime");
            strResult.append("cardNo : "        + callbackData.getQueryParameter("cardNo") + "\n");
            cardNum = callbackData.getQueryParameter("cardNo");
            strResult.append("instlmtMonth : "  + callbackData.getQueryParameter("instlmtMonth") + "\n");
            installement = callbackData.getQueryParameter("instlmtMonth");
            strResult.append("issuCmpnyCode : " + callbackData.getQueryParameter("issuCmpnyCode") + "\n");
            strResult.append("issuCmpnyNm : "   + callbackData.getQueryParameter("issuCmpnyNm") + "\n");
            strResult.append("puchasCmpnyCode : " + callbackData.getQueryParameter("puchasCmpnyCode") + "\n");
            strResult.append("puchasCmpnyNm : " + callbackData.getQueryParameter("puchasCmpnyNm") + "\n");
            purchaseName = callbackData.getQueryParameter("puchasCmpnyNm");

            String splpc = callbackData.getQueryParameter("splpc");
            if(null != splpc){
                strResult.append("splpc : "         + splpc + "\n");
            }else {
                strResult.append("total : "         + callbackData.getQueryParameter("total") + "\n");
                totalAmount = callbackData.getQueryParameter("total");
            }

            strResult.append("vat : "           + callbackData.getQueryParameter("vat") + "\n");
            vatAmount = callbackData.getQueryParameter("vat");
            supplyAmount = String.valueOf(Integer.parseInt(totalAmount) -  Integer.parseInt(vatAmount));

            strResult.append("aditInfo : "      + callbackData.getQueryParameter("aditInfo") + "\n");
            strResult.append("trmnlNo : "       + callbackData.getQueryParameter("trmnlNo") + "\n");
            terminalNum = callbackData.getQueryParameter("trmnlNo");
            strResult.append("bizrno : "        + callbackData.getQueryParameter("bizrno") + "\n");
            bizNum = callbackData.getQueryParameter("bizrno");
            strResult.append("bplcNm : "        + callbackData.getQueryParameter("bplcNm") + "\n");
            storeName = callbackData.getQueryParameter("bplcNm");
            strResult.append("rprsntvNm : "     + callbackData.getQueryParameter("rprsntvNm") + "\n");
            ownerName = callbackData.getQueryParameter("rprsntvNm");
            strResult.append("bplcAdres : "     + callbackData.getQueryParameter("bplcAdres") + "\n");
            storeAddress = callbackData.getQueryParameter("bplcAdres");
            strResult.append("bplcTelno : "     + callbackData.getQueryParameter("bplcTelno") + "\n");
            storeTel = callbackData.getQueryParameter("bplcTelno");

            memberNum = callbackData.getQueryParameter("partnerNo");
            if(null != memberNum){
                strResult.append("partnerNo : "     + memberNum + "\n");
            }
            this.referenceNo = callbackData.getQueryParameter("REFERENCE_NO");
            if(null != referenceNo){
                strResult.append("REFERENCE_NO : "     + callbackData.getQueryParameter("REFERENCE_NO") + "\n");
            }
            if("KAKAOPAY".equals(payType)){
                strResult.append("KakaoDiscount : " + callbackData.getQueryParameter("KakaoDiscount") + "\n");
                strResult.append("KakaoPayType : " + callbackData.getQueryParameter("KakaoPayType") + "\n");
            }
            if(null != callbackData.getQueryParameter("errorType")){
                strResult.append("errorType : " + callbackData.getQueryParameter("errorType") + "\n");
            }
        }
        return strResult.toString();
    }
}