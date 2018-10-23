package com.sundyn;

import com.sundyn.action.FormFile;
import com.sundyn.cer.CertifacateGenerate;
import com.sundyn.util.BaseCert;
import com.sundyn.utils.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import org.junit.Test;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.util.*;
import java.util.regex.Pattern;

public class test1 {
    /**
     * @param args
     */
    public static void main1(String[] args) {
        /*try {
            File imageFile = new File("d:/大厅版本排队信息发布终端数据接口协议V2017.6.8.pdf");
            String requestUrl = "http://localhost:8080/appries/appriesFileupload.action";
            //请求普通信息
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", "张三");
            params.put("pwd", "zhangsan");
            params.put("age", "21");
            params.put("fileName", imageFile.getName());
            //上传文件
            FormFile formfile = new FormFile(imageFile.getName(), imageFile, "image", "application/octet-stream");

            post(requestUrl, params, new FormFile[]{formfile});
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        int res = 0;
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        try {
            URL url = new URL("http://localhost:8080/appries/appriesFileupload.action");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            File file = new File("d:/大厅版本排队信息发布终端数据接口协议V2017.6.8.pdf");
            if (file != null) {
                /**
                 * 当文件不为空时执行上传
                 */
                DataOutputStream dos = new DataOutputStream(
                        conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */

                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + "UTF-8" + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                res = conn.getResponseCode();
                if (res == 200) {
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                } else {
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }

    /**
     * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能:
     *   <FORM METHOD=POST ACTION="http://192.168.1.101:8083/upload/servlet/UploadServlet" enctype="multipart/form-data">
     <INPUT TYPE="text" NAME="name">
     <INPUT TYPE="text" NAME="id">
     <input type="file" name="imagefile"/>
     <input type="file" name="zip"/>
     </FORM>
     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.iteye.cn或http://192.168.1.101:8083这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param file 上传文件
     */
    public static boolean post(String path, Map<String, String> params, FormFile[] files) throws Exception{
        final String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
        final String endline = "--" + BOUNDARY + "--\r\n";//数据结束标志

        int fileDataLength = 0;
        for(FormFile uploadFile : files){//得到文件类型数据的总长度
            StringBuilder fileExplain = new StringBuilder();
            fileExplain.append("--");
            fileExplain.append(BOUNDARY);
            fileExplain.append("\r\n");
            fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
            fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
            fileExplain.append("\r\n");
            fileDataLength += fileExplain.length();
            if(uploadFile.getInStream()!=null){
                fileDataLength += uploadFile.getFile().length();
            }else{
                fileDataLength += uploadFile.getData().length;
            }
        }
        StringBuilder textEntity = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {//构造文本类型参数的实体数据
            textEntity.append("--");
            textEntity.append(BOUNDARY);
            textEntity.append("\r\n");
            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
            textEntity.append(entry.getValue());
            textEntity.append("\r\n");
        }
        //计算传输给服务器的实体数据总长度
        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;

        URL url = new URL(path);
        int port = url.getPort()==-1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
        OutputStream outStream = socket.getOutputStream();
        //下面完成HTTP请求头的发送
        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: "+ dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
        outStream.write(host.getBytes());
        //写完HTTP请求头后根据HTTP协议再写一个回车换行
        outStream.write("\r\n".getBytes());
        //把所有文本类型的实体数据发送出来
        outStream.write(textEntity.toString().getBytes());
        //把所有文件类型的实体数据发送出来
        for(FormFile uploadFile : files){
            StringBuilder fileEntity = new StringBuilder();
            fileEntity.append("--");
            fileEntity.append(BOUNDARY);
            fileEntity.append("\r\n");
            fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
            fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
            outStream.write(fileEntity.toString().getBytes());
            if(uploadFile.getInStream()!=null){
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = uploadFile.getInStream().read(buffer, 0, 1024))!=-1){
                    outStream.write(buffer, 0, len);
                }
                uploadFile.getInStream().close();
            }else{
                outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
            }
            outStream.write("\r\n".getBytes());
        }
        //下面发送数据结束标志，表示数据已经结束
        outStream.write(endline.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if(reader.readLine().indexOf("200")==-1){//读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
            return false;
        }
        outStream.flush();
        outStream.close();
        reader.close();
        socket.close();
        return true;
    }

    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    public static void main2(String[] args) {
        logger.debug("Will not show.");
        logger.error("Hello, World!");

        logger.info("我是info信息");
        logger.warn("我是warn信息");
        logger.error("我是error信息");
        logger.fatal("我是fatal信息");
        int a=10, b=11;
        logger.printf(Level.TRACE, "%d+%d=%d", a, b, a + b);
    }

    private static String storepass = "storepass";
    private static String storepath = "1.keystore";
    private static String storealias = "testalias";
    public static void main(String[] args) throws Exception {
        FileInputStream in=new FileInputStream(storepath);
        KeyStore ks=KeyStore.getInstance("JKS");
        ks.load(in,storepass.toCharArray());
        Certificate cchain=ks.getCertificate(storealias);//获取别名对应条目的证书链
        PrivateKey pk=(PrivateKey)ks.getKey(storealias,storepass.toCharArray());//获取别名对应条目的私钥
        //ks.setKeyEntry(storealias,pk,storepass.toCharArray(),cchain);//向密钥库中添加条目

        /*FileInputStream in=new FileInputStream(storepath);
        KeyStore ks=KeyStore.getInstance("JKS");
        ks.load(in,storepass.toCharArray());
        ks.containsAlias("sage");//检验条目是否在密钥库中，存在返回true
        FileOutputStream output=new FileOutputStream(storepath);
        ks.store(output,storepass.toCharArray());*/

    }
    public static void main333(String[] args) throws Exception {
        BaseCert bc = new BaseCert();
        X509Certificate x = bc.generateCert("root");
        // 导出为 cer 证书
        try {
            FileOutputStream fos = new FileOutputStream("d:/zxroot.cer");
            fos.write(x.getEncoded());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        x = bc.generateCert("CCB8A8DB14F8");
        // 导出为 cer 证书
        try {
            FileOutputStream fos = new FileOutputStream("d:/CCB8A8DB14F8.cer");
            fos.write(x.getEncoded());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        //x = bc.generateCert("123");
        // 导出为 cer 证书
        /*try {
            FileOutputStream fos = new FileOutputStream("d:/t2.cer");
            fos.write(x.getEncoded());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
*/
        InputStream i1 = new FileInputStream("d:/zxroot.cer");
        InputStream i2 = new FileInputStream("d:/CCB8A8DB14F8.cer");

        X509Certificate x509Certificate = (X509Certificate) getCertificate(i1);
        x509Certificate.verify(((X509Certificate) getCertificate(i2)).getPublicKey());

        //byte[] decodedText = MyCertifacate.decode(encodedText, x509Certificate.getPublicKey());
        //System.out.println("Decoded Text : " + new String(decodedText));
        //System.out.println("Signature is : " + verify(receivedCertificate, decodedText, signature));
    }

    private static Certificate getCertificate(InputStream inputStream) throws Exception {
        return CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
    }

    @Test
    public void testsa(){
        try {
            CertifacateGenerate cer = new CertifacateGenerate();
            cer.GenRootKeyPair();//生成密钥
            cer.GenRootCert();//生成根证书
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testca(){
        try {
            CertifacateGenerate cer = new CertifacateGenerate();
            String userCerPub = "D:/certtest/test.public";
            String dnuser = "CN=广州车管所,OU=广州车管所,O=广州车管所,L=GuangZhou,ST=GuangDong,C=CN";
            cer.ZhangsanKeyPair(userCerPub,"D:/certtest/test.private");//生成密钥
            cer.GenZhangsanCert("D:/certtest/CCB8A8DB14F8.cer",new Date(System.currentTimeMillis()+ 100 * 24 * 60 * 60 * 1000),
                    userCerPub,dnuser);//生成用户证书
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testverify(){
        CertificateFactory certificateFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509","BC");

            //FileInputStream inStream = new FileInputStream("D:/certtest/ca.cer");
            //X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inStream);
            //System.out.println(certificate.getPublicKey().toString());

            FileInputStream inStream2 = new FileInputStream("D:/certtest/zhangsan.cer");//D:/certtest/CCB8A8DB14F8.cer
            X509Certificate certificate2 = (X509Certificate) certificateFactory.generateCertificate(inStream2);
            System.out.println(certificate2.getPublicKey().toString());

            Signature signature2 = Signature.getInstance(certificate2.getSigAlgName(),"BC");
            signature2.initVerify(getRootPublicKey());
            signature2.update(certificate2.getTBSCertificate());
            boolean legal2 = signature2.verify(certificate2.getSignature());
            System.out.println(legal2);

            certificate2.verify(getRootPublicKey());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PublicKey getRootPublicKey() throws Exception {
        return PublicKey.class.cast(readKey("d:/certtest/zxroot.public"));
    }

    public Key readKey(String path) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        Key key = Key.class.cast(ois.readObject());
        ois.close();
        return key;
    }
    @Test
    public void test1(){
        System.out.println("123");
        List list = new ArrayList();
        Map m = new HashMap();
        m.put("a", 1.1);
        m.put("b", 2.1);
        m.put("c", 3.1);
        list.add(m);

        m = new HashMap();
        m.put("a", 4.1);
        m.put("b", 5.1);
        m.put("c", 6.1);
        list.add(m);

        m = new HashMap();
        m.put("a", 9.1);
        m.put("b", 8.1);
        m.put("c", 7.1);
        list.add(m);

        for (Object o : list) {
            Map m2 = (Map)o;
            System.out.println("a:" + m2.get("a").toString() + " b:" + m2.get("b") + " c:" + m2.get("c"));
        }
        Collections.sort(list, new SortByServiceCount("c", "desc"));
        for (Object o : list) {
            Map m2 = (Map)o;
            System.out.println("a:" + m2.get("a").toString() + " b:" + m2.get("b") + " c:" + m2.get("c"));
        }

        System.out.println(StringUtils.isAlpha("123.4"));
        System.out.println(NumberUtils.isNumber("null"));
    }

    //方法一：用JAVA自带的函数
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    class SortByServiceCount implements Comparator {
        private String field;
        private String desc;

        public SortByServiceCount(String field, String desc) {
            this.field = field;
            this.desc = desc;
        }
        public int compare(Object o1, Object o2) {
            Map s1 = (Map) o1;
            Map s2 = (Map) o2;
            System.out.println(field + ", " + s1.get(field).toString() + ", " + s2.get(field).toString() + ", "
                    +(Float.parseFloat(s1.get(field).toString()) > Float.parseFloat(s2.get(field).toString())));
            int r = 1;
            if (Float.parseFloat(s1.get(field).toString()) > Float.parseFloat(s2.get(field).toString()))
                r = desc.equals("desc") ? 1 : -1;
            r = desc.equals("desc") ? -1 : 1;
            return r;
        }
    }

    @Test
    public void test2(){
        System.out.println((int)Math.floor(123.12));
    }
}
