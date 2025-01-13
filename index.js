/**
 * @providesModule RNStartActivityForFdik
 */

import { NativeModules } from "react-native";
var RNStartActivityForFdik = NativeModules.RNStartActivityForFdik || {};

var startActivityForFdik = (
    key, uri, action, cardCashSe, delngSe, total, 
    vat, taxxpt, instlmtMonth, callbackAppUr, 
    aditInfo, srcConfmNo, srcConfmDe, barcodeNum,
    cashNum, trmnlno, prdctNo, bizNo, uscMuf, REFERENCE_NO,
    KakaoDiscount, KakaoPayType, PaycoDiscount, PaycoPayType, cupDeposit) => {
    
    return RNStartActivityForFdik.startActivityForFdik(
        key, uri, action, cardCashSe, delngSe, total, vat, taxxpt, 
        instlmtMonth, callbackAppUr, aditInfo, srcConfmNo, srcConfmDe, barcodeNum,
        cashNum, trmnlno, prdctNo, bizNo, uscMuf, REFERENCE_NO,
        KakaoDiscount, KakaoPayType, PaycoDiscount, PaycoPayType, cupDeposit
    );
};

export default startActivityForFdik;