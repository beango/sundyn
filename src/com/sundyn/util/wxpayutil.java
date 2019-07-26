package com.sundyn.util;

import net.sf.json.JSON;
import org.jdom.JDOMException;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class wxpayutil {
    public void GetQrCode2(String no)
    {
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("appid", ConfigUtil.APPID);
        parameters.put("mch_id", "");
        parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
        parameters.put("body", "LEO测试NATIVE支付");
        parameters.put("out_trade_no", "20140107001");
        parameters.put("total_fee", "1");
        parameters.put("spbill_create_ip","127.0.0.1");
        parameters.put("notify_url", ConfigUtil.NOTIFY_URL);//支付成功后回调的action，与JSAPI相同
        parameters.put("trade_type", "NATIVE");
        parameters.put("product_id", "No.20140105003");//商品号要唯一
        String sign = PayCommonUtil.createSign("UTF-8", parameters);
        parameters.put("sign", sign);

        String requestXML = PayCommonUtil.getRequestXml(parameters);
        String result =CommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
        System.out.println(result);
        Map<String, String> map = null;
        try {
            map = XmlUtil.doXMLParse(result);
            String returnCode = map.get("return_code");
            String resultCode = map.get("result_code");
            if(returnCode.equalsIgnoreCase("SUCCESS")&&resultCode.equalsIgnoreCase("SUCCESS")){
                String codeUrl = map.get("code_url");
                //TODO 拿到codeUrl，写代码生成二维码
                System.out.println("codeUrl="+codeUrl);
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
