package com.anttivuor.startactivityforfdik;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;

public class RNStartActivityForFdikModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final String ERROR = "ERROR";
    private static final String ACTIVITY_DOES_NOT_EXIST = "ACTIVITY_DOES_NOT_EXIST";
    private static int REQUEST_CODE = 100;
    private static final int ACTIVITY_REQUEST_CODE = 4;
    public static final int MSG_REQUEST_OUTSIDEAPPR = 4;
    public static final int MSG_STATE_OK = 1;
    public static final int MSG_STATE_NG = 2;
    private String returnKey = "";
    private Uri callbackData = null;

    private Promise mPromise;

    private ReactApplicationContext reactContext;
    private Callback onComplete = null;

    public RNStartActivityForFdikModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        // this.reactContext.addActivityEventListener(mActivityEventListener);
    }

    // @Override
    // protected void onNewIntent(Intent intent) {
    //     super.onNewIntent(intent);
    //     CallbackDataProcess();
    // }

    // public void CallbackDataProcess(){
    //     if(null != getIntent()){

    //         if(null != getIntent().getData()){
    //             callbackData = getIntent().getData();
    //             mPromise.resolve(callbackData.toString());
    //             mPromise = null;
    //         }
    //     }
    // }

    @Override
    public void initialize() {
        super.initialize();
        getReactApplicationContext().addActivityEventListener(this);
    }

    @Override
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        getReactApplicationContext().removeActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "RNStartActivityForFdik";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("ERROR", ERROR);
        constants.put("ACTIVITY_DOES_NOT_EXIST", ACTIVITY_DOES_NOT_EXIST);
        return constants;
    }

    private String getTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
        return dayTime.format(new Date(time));
    }

    @ReactMethod
    public void startActivityForFdik(String key, String uri, String action, 
        String cardCashSe, String delngSe, String total, 
        String vat, String taxxpt, String instlmtMonth, String callbackAppUr, 
        String aditInfo, String srcConfmNo, String srcConfmDe, String barcodeNum,
        String cashNum, String trmnlno, String prdctNo, String bizNo, String uscMuf, 
        String REFERENCE_NO, String KakaoDiscount, String KakaoPayType, String PaycoDiscount, 
        String PaycoPayType, String cupDeposit, Promise promise) {

        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            return;
        }

        this.mPromise = promise;

        try {
            String callScheme = "fpispkpn://default";
            Log.d("fpispkpn-D", uri);
            String intentAction = action == null ? Intent.ACTION_VIEW : action;
                        
            StringBuilder builder = new StringBuilder();
            builder.append(callScheme+"?"+
                    "&cardCashSe="+cardCashSe+
                    "&delngSe="+delngSe+
                    "&total="+total+
                    "&vat="+vat+
                    "&instlmtMonth="+instlmtMonth+
                    "&callbackAppUrl="+callbackAppUr+
                    "&aditInfo="+aditInfo);

            if(delngSe == "0"){
                //취소 모드인 경우 취소 정보를 포함하여 전달
                builder.append("&srcConfmNo=" + srcConfmNo
                        +"&srcConfmDe=" + srcConfmDe + (0<REFERENCE_NO.length()?String.format("&REFERENCE_NO=%s",REFERENCE_NO):""));
            }
            
            String url = builder.toString();
            Log.e("urlLog",url);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));       

            // returnKey = key;
            Bundle dataBundle = paymentIntent.getExtras();

            currentActivity.startActivityForResult(intent, ACTIVITY_REQUEST_CODE, dataBundle);
        } catch (Exception e) {
            // JSONObject jsonObj = new JSONObject();
                    
            // jsonObj.put("rtn_ServerMsg1", e.getMessage());
            // jsonObj.put("rtn_LEDCode", "7070");

            // mPromise.resolve(jsonObj);
            e.printStackTrace();
            this.mPromise.reject(ERROR, e);
            this.mPromise = null;
        }
    }

    private static final JSONObject bundleToJson(Bundle bundle) {
        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
        try{
            json.put(key, bundle.get(key));
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        return json;
    }
  
    private static WritableArray convertJsonToArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = new WritableNativeArray();

        for (int i = 0; i < jsonArray.length(); i++) {
        Object value = jsonArray.get(i);
        if (value instanceof JSONObject) {
            array.pushMap(convertJsonToMap((JSONObject) value));
        } else if (value instanceof JSONArray) {
            array.pushArray(convertJsonToArray((JSONArray) value));
        } else if (value instanceof Boolean) {
            array.pushBoolean((Boolean) value);
        } else if (value instanceof Integer) {
            array.pushInt((Integer) value);
        } else if (value instanceof Double) {
            array.pushDouble((Double) value);
        } else if (value instanceof String) {
            array.pushString((String) value);
        } else {
            array.pushString(value.toString());
        }
        }
        return array;
    }

    private static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = new WritableNativeMap();

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
        String key = iterator.next();
        Object value = jsonObject.get(key);
        if (value instanceof JSONObject) {
            map.putMap(key, convertJsonToMap((JSONObject) value));
        } else if (value instanceof JSONArray) {
            map.putArray(key, convertJsonToArray((JSONArray) value));
        } else if (value instanceof Boolean) {
            map.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            map.putInt(key, (Integer) value);
        } else if (value instanceof Double) {
            map.putDouble(key, (Double) value);
        } else if (value instanceof String) {
            map.putString(key, (String) value);
        } else {
            map.putString(key, value.toString());
        }
        }
        return map;
    }

    // private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    //     @Override
    //     public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            
    //         // Uri callbackData = null;
    //         // if (requestCode == MSG_REQUEST_OUTSIDEAPPR) {
    //         //     if (resultCode == MSG_STATE_OK) {
    //         //         Log.d("fpispkpn-D","dKKKKKKKKKKKKKKKKKKdd");
    //         //         JSONObject jsonObj = new JSONObject();

    //         //         jsonObj.put("cardCashSe", data.getStringExtra("cardCashSe"));
    //         //         jsonObj.put("delngSe", data.getStringExtra("delngSe"));
    //         //         jsonObj.put("setleSuccesAt", data.getStringExtra("setleSuccesAt"));
    //         //         jsonObj.put("setleMessage", data.getStringExtra("setleMessage"));
    //         //         jsonObj.put("confmNo", data.getStringExtra("confmNo"));
    //         //         jsonObj.put("confmDe", data.getStringExtra("confmDe"));
    //         //         jsonObj.put("confmTime", data.getStringExtra("confmTime"));
    //         //         jsonObj.put("cardNo", data.getStringExtra("cardNo"));
    //         //         jsonObj.put("instlmtMonth", data.getStringExtra("instlmtMonth"));
    //         //         jsonObj.put("partnerNo", data.getStringExtra("partnerNo"));
    //         //         jsonObj.put("issuCmpnyCode", data.getStringExtra("issuCmpnyCode"));
    //         //         jsonObj.put("issuCmpnyNm", data.getStringExtra("issuCmpnyNm"));
    //         //         jsonObj.put("puchasCmpnyCode", data.getStringExtra("puchasCmpnyCode"));
    //         //         jsonObj.put("puchasCmpnyNm", data.getStringExtra("puchasCmpnyNm"));
    //         //         jsonObj.put("cardNm", data.getStringExtra("cardNm"));
    //         //         jsonObj.put("aditInfo", data.getStringExtra("aditInfo"));
    //         //         jsonObj.put("trmnlNo", data.getStringExtra("trmnlNo"));
    //         //         jsonObj.put("bizrno", data.getStringExtra("bizrno"));
    //         //         jsonObj.put("bplcNm", data.getStringExtra("bplcNm"));
    //         //         jsonObj.put("rprsntvNm", data.getStringExtra("rprsntvNm"));
    //         //         jsonObj.put("bplcAdres", data.getStringExtra("bplcAdres"));
    //         //         jsonObj.put("bplcTelno", data.getStringExtra("bplcTelno"));
    //         //         jsonObj.put("transAmntCny", data.getStringExtra("transAmntCny"));
    //         //         jsonObj.put("exchngRate", data.getStringExtra("exchngRate"));
    //         //         jsonObj.put("alipayTransId", data.getStringExtra("alipayTransId"));
    //         //         jsonObj.put("prtnrTransId", data.getStringExtra("prtnrTransId"));
    //         //         jsonObj.put("REFERENCE_NO", data.getStringExtra("REFERENCE_NO"));
    //         //         jsonObj.put("uscMuf", data.getStringExtra("uscMuf"));
    //         //         jsonObj.put("total", data.getStringExtra("total"));
    //         //         jsonObj.put("vat", data.getStringExtra("vat"));
    //         //         jsonObj.put("taxxpt", data.getStringExtra("taxxpt"));
    //         //         jsonObj.put("KakaoDiscount", data.getStringExtra("KakaoDiscount"));
    //         //         jsonObj.put("KakaoPayType", data.getStringExtra("KakaoPayType"));
    //         //         jsonObj.put("PaycoDiscount", data.getStringExtra("PaycoDiscount"));
    //         //         jsonObj.put("PaycoPayType", data.getStringExtra("PaycoPayType"));
    //         //         jsonObj.put("CupDeposit", data.getStringExtra("CupDeposit"));
    //         //         jsonObj.put("TagSeria", data.getStringExtra("TagSeria"));

    //         //         mPromise.resolve(jsonObj);
    //         //         mPromise = null;

    //         //     }else if(resultCode == MSG_STATE_NG){
    //         //         Log.d("fpispkpn-D","dNNNNNNNNNNNNNNNNNNNNNNd");
    //         //         JSONObject jsonObj = new JSONObject();
                    
    //         //         jsonObj.put("rtn_ServerMsg1", data.getStringExtra("rtn_ServerMsg1"));
    //         //         jsonObj.put("rtn_LEDCode", data.getStringExtra("rtn_LEDCode"));

    //         //         // String errMSG = data.getStringExtra("rtn_ServerMsg1");
    //         //         // String errCODE = data.getStringExtra("rtn_LEDCode");
    //         //         // String Msgebuf = "[" + errCODE + "] : " + errMSG;
    //         //         // Toast.makeText(this, Msgebuf, Toast.LENGTH_SHORT).show();
                    
    //         //         mPromise.resolve(jsonObj);
    //         //         mPromise = null;

    //         //     }else{
    //         //         //Log.d(TAG,"resultCode = "+ resultCode);
    //         //         JSONObject jsonObj = new JSONObject();
                    
    //         //         jsonObj.put("rtn_ServerMsg1", "문제발생.. 직원에게 문의하세요.");
    //         //         jsonObj.put("rtn_LEDCode", "6060");

    //         //         mPromise.resolve(jsonObj);
    //         //         mPromise = null;
    //         //     }
    //         // }

    //         try {
    //             mPromise.resolve(data);
    //             mPromise = null;
    //             // if (requestCode == MSG_REQUEST_OUTSIDEAPPR) {
    //             // if (resultCode == MSG_STATE_OK) {
    //             //     Log.d("fpispkpn-D","dKKKKKKKKKKKKKKKKKKdd");
    //             //     // JSONObject jsonObj = new JSONObject();

    //             //     // jsonObj.put("cardCashSe", data.getStringExtra("cardCashSe"));
    //             //     // jsonObj.put("delngSe", data.getStringExtra("delngSe"));
    //             //     // jsonObj.put("setleSuccesAt", data.getStringExtra("setleSuccesAt"));
    //             //     // jsonObj.put("setleMessage", data.getStringExtra("setleMessage"));
    //             //     // jsonObj.put("confmNo", data.getStringExtra("confmNo"));
    //             //     // jsonObj.put("confmDe", data.getStringExtra("confmDe"));
    //             //     // jsonObj.put("confmTime", data.getStringExtra("confmTime"));
    //             //     // jsonObj.put("cardNo", data.getStringExtra("cardNo"));
    //             //     // jsonObj.put("instlmtMonth", data.getStringExtra("instlmtMonth"));
    //             //     // jsonObj.put("partnerNo", data.getStringExtra("partnerNo"));
    //             //     // jsonObj.put("issuCmpnyCode", data.getStringExtra("issuCmpnyCode"));
    //             //     // jsonObj.put("issuCmpnyNm", data.getStringExtra("issuCmpnyNm"));
    //             //     // jsonObj.put("puchasCmpnyCode", data.getStringExtra("puchasCmpnyCode"));
    //             //     // jsonObj.put("puchasCmpnyNm", data.getStringExtra("puchasCmpnyNm"));
    //             //     // jsonObj.put("cardNm", data.getStringExtra("cardNm"));
    //             //     // jsonObj.put("aditInfo", data.getStringExtra("aditInfo"));
    //             //     // jsonObj.put("trmnlNo", data.getStringExtra("trmnlNo"));
    //             //     // jsonObj.put("bizrno", data.getStringExtra("bizrno"));
    //             //     // jsonObj.put("bplcNm", data.getStringExtra("bplcNm"));
    //             //     // jsonObj.put("rprsntvNm", data.getStringExtra("rprsntvNm"));
    //             //     // jsonObj.put("bplcAdres", data.getStringExtra("bplcAdres"));
    //             //     // jsonObj.put("bplcTelno", data.getStringExtra("bplcTelno"));
    //             //     // jsonObj.put("transAmntCny", data.getStringExtra("transAmntCny"));
    //             //     // jsonObj.put("exchngRate", data.getStringExtra("exchngRate"));
    //             //     // jsonObj.put("alipayTransId", data.getStringExtra("alipayTransId"));
    //             //     // jsonObj.put("prtnrTransId", data.getStringExtra("prtnrTransId"));
    //             //     // jsonObj.put("REFERENCE_NO", data.getStringExtra("REFERENCE_NO"));
    //             //     // jsonObj.put("uscMuf", data.getStringExtra("uscMuf"));
    //             //     // jsonObj.put("total", data.getStringExtra("total"));
    //             //     // jsonObj.put("vat", data.getStringExtra("vat"));
    //             //     // jsonObj.put("taxxpt", data.getStringExtra("taxxpt"));
    //             //     // jsonObj.put("KakaoDiscount", data.getStringExtra("KakaoDiscount"));
    //             //     // jsonObj.put("KakaoPayType", data.getStringExtra("KakaoPayType"));
    //             //     // jsonObj.put("PaycoDiscount", data.getStringExtra("PaycoDiscount"));
    //             //     // jsonObj.put("PaycoPayType", data.getStringExtra("PaycoPayType"));
    //             //     // jsonObj.put("CupDeposit", data.getStringExtra("CupDeposit"));
    //             //     // jsonObj.put("TagSeria", data.getStringExtra("TagSeria"));

    //             //     mPromise.resolve(data.toString());
    //             //     mPromise = null;

    //             // }else if(resultCode == MSG_STATE_NG){
    //             //     Log.d("fpispkpn-D","dNNNNNNNNNNNNNNNNNNNNNNd");
    //             //     // JSONObject jsonObj = new JSONObject();
                    
    //             //     // jsonObj.put("rtn_ServerMsg1", data.getStringExtra("rtn_ServerMsg1"));
    //             //     // jsonObj.put("rtn_LEDCode", data.getStringExtra("rtn_LEDCode"));

    //             //     // String errMSG = data.getStringExtra("rtn_ServerMsg1");
    //             //     // String errCODE = data.getStringExtra("rtn_LEDCode");
    //             //     // String Msgebuf = "[" + errCODE + "] : " + errMSG;
    //             //     // Toast.makeText(this, Msgebuf, Toast.LENGTH_SHORT).show();
                    
    //             //     mPromise.resolve(data.toString());
    //             //     mPromise = null;

    //             // }else{
    //             //     //Log.d(TAG,"resultCode = "+ resultCode);
    //             //     // JSONObject jsonObj = new JSONObject();
                    
    //             //     // jsonObj.put("rtn_ServerMsg1", "문제발생.. 직원에게 문의하세요.");
    //             //     // jsonObj.put("rtn_LEDCode", "6060");

    //             //     mPromise.resolve(data.toString());
    //             //     mPromise = null;
    //             // }
    //         // }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             // JSONObject jsonObj = new JSONObject();
                    
    //             // jsonObj.put("rtn_ServerMsg1", "문제발생.. 직원에게 문의하세요.");
    //             // jsonObj.put("rtn_LEDCode", "6060");

    //             mPromise.reject(e);
    //             mPromise = null;
    //         }
    //     }        
    // };

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        this.mPromise.resolve(data.getDataString());
        this.mPromise = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}