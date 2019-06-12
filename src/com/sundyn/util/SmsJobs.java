package com.sundyn.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.cgssms.TrusteeshipService;
import com.sundyn.cgssms.TrusteeshipServiceLocator;
import com.sundyn.cgssms.Trusteeship_PortType;
import com.sundyn.entity.SysSmsdetail;
import com.sundyn.service.ISysSmsdetailService;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SmsJobs {
    private static String smsInte = "http://10.41.168.20:9080/NWTrusteeship/services/Trusteeship?wsdl";
    private static String apiCode2 = "sms";

    @Resource
    private ISysSmsdetailService smsdetailService;
    //private SMSService smsService;

    public void SendSmsTask(){
        List<SysSmsdetail> smsUnSendList = smsdetailService.selectList(new EntityWrapper<SysSmsdetail>().isNull("sendresult"));
        if (smsUnSendList!=null && smsUnSendList.size()>0){
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "发送短信★★★★★★★★★★★" + smsUnSendList.size());
            for (SysSmsdetail sms: smsUnSendList) {

                Object[] rst = SendSms(sms.getMobile(), sms.getContent(), sms.getSmId(), apiCode2);

                sms.setSendresult((Integer)rst[0]);
                sms.setSendtime(new Date());
                sms.setSenddesc(rst[1].toString());
                sms.updateById();
            }
        }
    }

    public static Object[] SendSms(String mobile, String content, int smID, String _apiCode){
        URL adress = null;//url为webservice接口地址
        Object[] rst = new Object[2];
        try {
            adress = new URL(smsInte);
            TrusteeshipService nwService = new TrusteeshipServiceLocator();//服务接口
            //方法接口
            Trusteeship_PortType nwTrusteeship= nwService.getTrusteeship(adress);
            //调用发送单条短信的方法，实现短信的发送
            int code = nwTrusteeship.sendMessage(mobile, content, smID, 0, _apiCode);
            rst[0] = code;
            rst[1] = "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            rst[0] = -9999;
            rst[1] = e.getMessage();
        } catch (ServiceException e) {
            e.printStackTrace();
            rst[0] = -9999;
            rst[1] = e.getMessage();
        } catch (RemoteException e) {
            e.printStackTrace();
            rst[0] = -9999;
            rst[1] = e.getMessage();
        }
        if (rst[1].toString().length() > 200)
            rst[1] = rst[1].toString().substring(0, 200);
        return rst;
    }

    public static void main(String[] args) {
        String s= "123";
        if (s.length()>2)
            s = s.substring(0, 2);
        System.out.println(s);
    }
}
