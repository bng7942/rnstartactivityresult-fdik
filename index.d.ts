
declare type startActivityForResult = (
    key:String, 
    uri:String, 
    action:String, 
    cardCashSe:String, 
    delngSe:String, 
    total:String, 
    vat:String, 
    taxxpt:String, 
    instlmtMonth:String, 
    callbackAppUr:String, 
    aditInfo:String, 
    srcConfmNo:String, 
    srcConfmDe:String, 
    barcodeNum:String,
    cashNum:String, 
    trmnlno:String, 
    prdctNo:String, 
    bizNo:String, 
    uscMuf:String, 
    REFERENCE_NO:String,
    KakaoDiscount:String, 
    KakaoPayType:String, 
    PaycoDiscount:String, 
    PaycoPayType:String, 
    cupDeposit:String
) => void

export = startActivityForResult
