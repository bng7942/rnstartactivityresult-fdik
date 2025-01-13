/**
 * @providesModule RNStartActivityForResult
 */

import { NativeModules } from "react-native";
var RNStartActivityForResult = NativeModules.RNStartActivityForResult || {};

var startActivityForResult = (
    key, uri, action, cardCashSe, delngSe, total, 
    vat, taxxpt, instlmtMonth, callbackAppUr, 
    aditInfo, srcConfmNo, srcConfmDe, barcodeNum,
    cashNum, trmnlno, prdctNo, bizNo, uscMuf, REFERENCE_NO,
    KakaoDiscount, KakaoPayType, PaycoDiscount, PaycoPayType, cupDeposit) => {
    
    return RNStartActivityForResult.startActivityForResult(
        key, uri, action, cardCashSe, delngSe, total, vat, taxxpt, 
        instlmtMonth, callbackAppUr, aditInfo, srcConfmNo, srcConfmDe, barcodeNum,
        cashNum, trmnlno, prdctNo, bizNo, uscMuf, REFERENCE_NO,
        KakaoDiscount, KakaoPayType, PaycoDiscount, PaycoPayType, cupDeposit
    );
};

export default startActivityForResult;