package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.entity.City;
import com.sundyn.entity.Province;
import com.sundyn.service.*;
import com.sundyn.util.Pager;
import com.sundyn.util.SundynSet;
import com.sundyn.util.socketUdp;
import com.sundyn.utils.CitysUtils;
import com.sundyn.utils.GetWeatherString;
import com.sundyn.vo.AppriesVo;
import com.xuan.xutils.utils.StringUtils;
import com.xuan.xutils.utils.UUIDUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppriesAction extends ActionSupport
{
    private static final Logger logger;
    private static final long serialVersionUID = 1L;
    private static final Integer pageSize;
    private AppriesService appriesService;
    private DeptService deptService;
    private EmployeeService employeeService;
    private ErrorInfoService errorInfoService;
    private KeyTypeService keyTypeService;
    private ManagerService managerService;
    private PowerService powerService;
    private String msg;
    private QueryService queryService;
    private String startDate;
    private String endDate;
    private Integer id;
    private Pager pager;
    private String mobileIp;
    private int mobilePort;
    private int clientPort;
    private String str;
    private City city;
    private Province province;
    private CitysUtils cityutils;
    
    static {
        logger = Logger.getLogger((Class)AppriesAction.class.getClass());
        pageSize = 6;
    }
    
    public int getMobilePort() {
        return this.mobilePort;
    }
    
    public void setMobilePort(final int mobilePort) {
        this.mobilePort = mobilePort;
    }
    
    public int getClientPort() {
        return this.clientPort;
    }
    
    public void setClientPort(final int clientPort) {
        this.clientPort = clientPort;
    }
    
    public String getMobileIp() {
        return this.mobileIp;
    }
    
    public void setMobileIp(final String mobileIp) {
        this.mobileIp = mobileIp;
    }
    
    private String getCamera() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String camera = "true";
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            camera = sundynSet.getM_system().get("camera").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return camera;
    }
    
    public AppriesService getAppriesService() {
        return this.appriesService;
    }
    
    public void setAppriesService(final AppriesService appriesService) {
        this.appriesService = appriesService;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }
    
    public void setEmployeeService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    public ErrorInfoService getErrorInfoService() {
        return this.errorInfoService;
    }
    
    public void setErrorInfoService(final ErrorInfoService errorInfoService) {
        this.errorInfoService = errorInfoService;
    }
    
    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }
    
    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }
    
    public ManagerService getManagerService() {
        return this.managerService;
    }
    
    public void setManagerService(final ManagerService managerService) {
        this.managerService = managerService;
    }
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public static long getSerialVersionUID() {
        return 1L;
    }
    
    public QueryService getQueryService() {
        return this.queryService;
    }
    
    public void setQueryService(final QueryService queryService) {
        this.queryService = queryService;
    }
    
    public String getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Pager getPager() {
        return this.pager;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public static Integer getPageSize() {
        return AppriesAction.pageSize;
    }
    
    public String appriesAdd() {
        System.out.println("++++++++++++++++1");
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String serviceDate = request.getParameter("serviceDate");
        final String serviceCycle = request.getParameter("serviceCycle");
        final String CustorTime = request.getParameter("CustorTime");
        final String DeptId = request.getParameter("DeptId");
        final String EmployeeId = request.getParameter("EmployeeId");
        final String serviceType = request.getParameter("serviceType");
        final String appriesTime = request.getParameter("appriesTime");
        final String updateTime = request.getParameter("updateTime");
        final String JieshouTime = request.getParameter("JieshouTime");
        final String saveTime = request.getParameter("saveTime");
        final String remark = request.getParameter("remark");
        final String deptarl = request.getParameter("deptarl");
        final String ext1 = request.getParameter("ext1");
        final String ext2 = request.getParameter("ext2");
        final String ext3 = request.getParameter("ext3");
        final String ext4 = request.getParameter("ext4");
        final String keyno = request.getParameter("keyno");
        final String mechinetype = request.getParameter("mechinetype");
        final AppriesVo appriesVo = new AppriesVo();
        appriesVo.setServiceDate(serviceDate);
        appriesVo.setServiceCycle(serviceCycle);
        appriesVo.setCustorTime(CustorTime);
        appriesVo.setDeptId(DeptId);
        appriesVo.setEmployeeId(EmployeeId);
        appriesVo.setServiceType(serviceType);
        appriesVo.setAppriesTime(appriesTime);
        appriesVo.setUpdateTime(updateTime);
        appriesVo.setJieshouTime(JieshouTime);
        appriesVo.setSaveTime(saveTime);
        appriesVo.setRemark(remark);
        appriesVo.setDeptarl(deptarl);
        appriesVo.setExt1(ext1);
        appriesVo.setExt2(ext2);
        appriesVo.setExt3(ext3);
        appriesVo.setExt4(ext4);
        appriesVo.setKeyno(keyno);
        appriesVo.setMechinetype(mechinetype);
        if (this.appriesService.addAppries(appriesVo)) {
            this.msg = "success";
        }
        else {
            this.msg = "error";
        }
        return "success";
    }
    public List<String> list = new ArrayList<String>();
    private File file;
    private String fileContentType;
    private String fileFileName;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public List<String> getList() {
        return list;
    }
    private String savePath;

    public String appriesFileupload()
    {
        System.out.println("获取Android端传过来的普通信息：111111111111111");
        System.out.println("-----------------");
        System.out.println(fileFileName + "------------------" + file.length());
        //HttpServletRequest request=ServletActionContext.getRequest();
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            System.out.println("获取Android端传过来的普通信息：");
            System.out.println("文件名："+fileFileName);
            System.out.println("获取Android端传过来的文件信息：");
            String uploadpath = ServletActionContext.getRequest().getRealPath(
                    "download/recorder");
            System.out.println(uploadpath);
            if (!new File(uploadpath).exists())
                new File(uploadpath).mkdir();
            File fs = new File(uploadpath, fileFileName);
            fos = new FileOutputStream(fs);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            System.out.println("文件上传成功");
        } catch (Exception e) {
            System.out.println("文件上传失败");
            e.printStackTrace();
        } finally {
            close(fos, fis);
        }
        return "success";
    }

    private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
                fis=null;
            } catch (IOException e) {
                System.out.println("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
                fis=null;
            } catch (IOException e) {
                System.out.println("FileOutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }

    public String appriesFileupload1() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = "E:/abc";
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath+"目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
        //消息提示
        String message = "";
        try{
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                System.out.println("没有文件上传");
                return "success";
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传成功！";
                }
            }
        }catch (Exception e) {
            System.out.println("文件上传失败"+ e.getMessage());
            message= "文件上传失败！";
            e.printStackTrace();

        }
        return "success";
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

    public String appriesAddContact()
    {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String tt = request.getParameter("tt").replace("%20"," ");
        final String name = request.getParameter("name");
        final String phone = request.getParameter("phone");
        String remark = request.getParameter("remark");
        if(remark!=null){
            remark = remark.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
        }
        final String mac = request.getParameter("mac");
        boolean bool = appriesService.addAppriesContact(mac, tt, name, phone, remark);
        this.msg = bool ? "提交成功":"提交失败";
        return "success";
    }

    public String appriesAddSpByPantryn() throws Exception {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String sam = "";
        String eam = "";
        String spm = "";
        String epm = "";
        String tipLanguage = "";
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            sam = sundynSet.getM_work2().get("sam").toString();
            eam = sundynSet.getM_work2().get("eam").toString();
            spm = sundynSet.getM_work2().get("spm").toString();
            epm = sundynSet.getM_work2().get("epm").toString();
            tipLanguage = sundynSet.getM_system().get("tipLanguage");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final String time = new SimpleDateFormat("HH:mm").format(new java.util.Date());
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        final String tt = request.getParameter("tt");
        final String cardnum = request.getParameter("cardnum");
        final String pj = request.getParameter("pj");
        final String tel = request.getParameter("tel");
        final String idCard = request.getParameter("idCard");
        final String name = request.getParameter("name");
        final String phone = request.getParameter("phone");
        final String demo = request.getParameter("content");
        String businessType = request.getParameter("businessType");
        final String videofile = request.getParameter("videofile2");
        String businessTime = request.getParameter("businessTime");
        String imgfile = request.getParameter("imgfile");
        String busRst = request.getParameter("busRst");
        String ywlsh = request.getParameter("ywlsh");
        if (ywlsh.equalsIgnoreCase("null"))
            ywlsh = null;
        int min = 0;
        int sec = 0;
        if (businessTime != null) {
            min = Integer.valueOf(businessTime) / 60;
            sec = Integer.valueOf(businessTime) % 60;
            businessTime = String.valueOf(String.valueOf(min)) + "\u5206" + String.valueOf(sec) + "\u79d2";
        }
        final String cf = request.getParameter("cf");
        if (businessType == null) {
            businessType = "1";
        }
        boolean k7 = true;
        try {
            final SundynSet sundynSet2 = SundynSet.getInstance(path);
            k7 = Boolean.valueOf(sundynSet2.getM_system().get("k7").toString());
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        final List bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        String bmk = "";
        if (k7) {
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                if (!i.get("keyNo").toString().equals("6")) {
                    bmk += i.get("keyNo").toString() + ",";
                }
            }
        }
        else {
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                bmk += i.get("keyNo").toString() + ",";
            }
        }
        if (bmk.indexOf(pj) > -1) {
            try {
                final Map appries = this.appriesService.getAppriesInfo(cardnum, mac, pj);
                final Map temp = new HashMap();
                final List host = this.managerService.getuserHost();
                final socketUdp udp = new socketUdp();
                if (tipLanguage.equals("") || tipLanguage.equals("zh")) {
                    this.msg = tt + '\r' + '\n' + "\u5927\u5385:" + appries.get("dating") + '\r' + '\n' + "\u7a97\u53e3:" + appries.get("window") + '\r' + '\n' + "\u804c\u5458:" + appries.get("Name") + '\r' + '\n' + appries.get("keyname") + '\r' + '\n';
                }
                else {
                    this.msg = "Evaluation time:" + tt + '\r' + '\n' + "evaluated hall:" + appries.get("dating") + '\r' + '\n' + "evaluated window:" + appries.get("window") + '\r' + '\n' + "evaluated employee:" + appries.get("Name") + '\r' + '\n' + " unsatisfied description " + appries.get("keyname") + '\r' + '\n';
                }
                if (host != null) {
                    for (int l = 0; l < host.size(); ++l) {
                    	Map map = (Map)host.get(l);
                    	final String ip = map.get("ext2").toString();
                        final String[] sip = ip.split(",");
                        for (int m = 0; m < sip.length; ++m) {
                            temp.put(sip[m], sip[m]);
                        }
                    }
                }
                for (final Object key : temp.keySet()) {
                    final String mb = temp.get(key).toString();
                    if (!mb.equals("")) {
                        udp.send(mb, this.clientPort, this.msg);
                    }
                }
                final List mobile = this.managerService.getuserMobile();
                final List l_m = new ArrayList();
                if (mobile != null) {
                    for (int i2 = 0; i2 < mobile.size(); ++i2) {
                    	Map map = (Map)mobile.get(i2);
                        //final String mobiles = mobile.get(i2).get("ext1").toString();
                    	final String mobiles = map.get("ext1").toString();
                        final String[] smobile = mobiles.split(",");
                        for (int j2 = 0; j2 < smobile.length; ++j2) {
                            l_m.add(smobile[j2]);
                        }
                    }
                }
                String s_m = "";
                for (int i3 = 0; i3 < l_m.size(); ++i3) {
                    s_m = String.valueOf(s_m) + l_m.get(i3).toString() + ",";
                }
                if (s_m.endsWith(",")) {
                    s_m = s_m.substring(0, s_m.length() - 1);
                }
                if (this.mobileIp.equals("")) {
                    final InetAddress addr = InetAddress.getLocalHost();
                    this.mobileIp = addr.getHostAddress();
                }
                final String t = this.msg + "||" + s_m;
                udp.send(this.mobileIp, this.mobilePort, this.msg + "||" + s_m);
                udp.close();
            }
            catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        String msg2 = "1";
        if (!sam.equals("") && !eam.equals("") && !spm.equals("") && !epm.equals("")) {
            if ((time.compareTo(sam) >= 0 && time.compareTo(eam) <= 0) || (time.compareTo(spm) >= 0 && time.compareTo(epm) <= 0)) {
                if (mac == null || mac.isEmpty()) {
                    this.msg = "error:mac\u4e3a\u7a7a";
                    msg2 = this.getText("sundyn.query.error.noMac");
                }
                else if (tt == null || tt.isEmpty()) {
                    this.msg = "error:tt\u4e3a\u7a7a";
                    msg2 = this.getText("sundyn.query.error.noTime");
                }
                else if (cardnum == null || cardnum.isEmpty()) {
                    this.msg = "error:cardnum\u4e3a\u7a7a";
                    msg2 = this.getText("sundyn.query.error.noCarNum");
                }
                else if (pj == null || pj.isEmpty()) {
                    this.msg = "error:pj\u4e3a\u7a7a";
                    msg2 = this.getText("sundyn.query.error.noPJ");
                }
                else if (this.deptService.findByMac(mac) == null) {
                    this.msg = "\u6ca1\u6709\u7a97\u53e3\u4f7f\u7528\u6b64mac";
                    msg2 = this.getText("sundyn.query.error.wrongMac");
                }
                else if (this.employeeService.findByCardnum(cardnum) == null) {
                    this.msg = "\u6ca1\u6709\u5458\u5de5\u4f7f\u7528\u6b64\u5361\u53f7";
                    msg2 = this.getText("sundyn.query.error.wrongCarNum");
                }
                else {
                    this.msg = "error:mac=" + mac + "--tt=" + tt + "--cardnum=" + cardnum + "--pj=" + pj;
                }
                System.out.println("++++++++++++++++this.msg// " + this.msg + "------------msg2: " + msg2 + ", ywlsh: " + ywlsh);
                if (msg2 == "1") {
                    final List list = this.appriesService.checkAppries(tt, pj, mac);
                    if (list != null) {
                        if (list.size() > 0) {
                            this.msg = "error:mac=" + mac + "--tt=" + tt + "--cardnum=" + cardnum + "--pj=" + pj + " has been saved";
                        }
                        else if (list.size() == 0) {
                            if (StringUtils.isBlank(ywlsh)){
                                ywlsh = UUIDUtils.getUUID();
                            }
                            if (this.appriesService.addArriresXiangYang(mac, tt, cardnum, pj, demo, videofile, businessTime, min, sec, tel, idCard,
                                    name, phone, imgfile, busRst, ywlsh, null)) {
                                this.msg = "success";
                            }
                            else {
                                msg2 = this.getText("sundyn.query.error.wrongDB");
                                final boolean flag = this.errorInfoService.addDetail(mac, cardnum, pj, msg2);
                            }
                        }
                    }
                }
                else {
                    this.errorInfoService.addDetail(mac, cardnum, pj, msg2);
                }
            }
            else {
                this.msg = "\u5f53\u524d\u4e3a\u4e0b\u73ed\u65f6\u95f4";
                msg2 = this.getText("sundyn.query.error.wrongWorkTime");
                this.errorInfoService.addDetail(mac, cardnum, pj, msg2);
            }
        }
        else {
            this.msg = "\u4e0a\u4e0b\u73ed\u65f6\u95f4\u4e3a\u7a7a";
            msg2 = this.getText("sundyn.query.error.noWorkTime");
            this.errorInfoService.addDetail(mac, cardnum, pj, msg2);
        }
        return "success";
    }
    
    public String appriesAddSp2() throws Exception {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String sam = "";
        String eam = "";
        String spm = "";
        String epm = "";
        String tipLanguage = "";
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            sam = sundynSet.getM_work2().get("sam").toString();
            eam = sundynSet.getM_work2().get("eam").toString();
            spm = sundynSet.getM_work2().get("spm").toString();
            epm = sundynSet.getM_work2().get("epm").toString();
            tipLanguage = sundynSet.getM_system().get("tipLanguage");
            AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0a\u73ed\u65f6\u95f4:sam:" + sam + "eam:" + eam + "spm:" + spm + "epm:" + epm));
        }
        catch (Exception e) {
            AppriesAction.logger.debug((Object)"\u5f97\u5230\u4e0a\u73ed\u65f6\u95f4\u51fa\u9519,\u8bf7\u68c0\u67e5update/set.xml  \u662f\u5426\u6b63\u786e");
            e.printStackTrace();
        }
        final String time = new SimpleDateFormat("HH:mm").format(new java.util.Date());
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        final String tt = request.getParameter("tt");
        final String cardnum = request.getParameter("cardnum");
        final String pj = request.getParameter("pj");
        final String demo = request.getParameter("demo");
        String businessType = request.getParameter("businessType");
        final String videofile = request.getParameter("videofile");
        String businessTime = request.getParameter("businessTime");
        int min = 0;
        int sec = 0;
        if (businessTime != null) {
            min = Integer.valueOf(businessTime) / 60;
            sec = Integer.valueOf(businessTime) % 60;
            businessTime = String.valueOf(String.valueOf(min)) + "\u5206" + String.valueOf(sec) + "\u79d2";
        }
        final String cf = request.getParameter("cf");
        if (businessType == null) {
            businessType = "1";
        }
        boolean k7 = true;
        try {
            final SundynSet sundynSet2 = SundynSet.getInstance(path);
            k7 = Boolean.valueOf(sundynSet2.getM_system().get("k7").toString());
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        final List bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        String bmk = "";
        AppriesAction.logger.info((Object)"\u5224\u65ad\u662f\u5426\u4f7f\u7528\u672a\u8bc4\u4ef7\u6570\u636e");
        if (k7) {
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                if (!i.get("keyNo").toString().equals("6")) {
                    bmk = String.valueOf(bmk) + i.get("keyNo").toString() + ",";
                    AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0d\u6ee1\u610fids" + bmk));
                }
            }
        }
        else {
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                bmk = String.valueOf(bmk) + i.get("keyNo").toString() + ",";
                AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0d\u6ee1\u610fids" + bmk));
            }
        }
        if (bmk.indexOf(pj) > -1) {
            try {
                final Map appries = this.appriesService.getAppriesInfo(cardnum, mac, pj);
                final Map temp = new HashMap();
                final List host = this.managerService.getuserHost();
                final socketUdp udp = new socketUdp();
                if (tipLanguage.equals("") || tipLanguage.equals("zh")) {
                    this.msg = tt + '\r' + '\n' + "\u5927\u5385:" + appries.get("dating") + '\r' + '\n' + "\u7a97\u53e3:" + appries.get("window") + '\r' + '\n' + "\u804c\u5458:" + appries.get("Name") + '\r' + '\n' + appries.get("keyname") + '\r' + '\n';
                }
                else {
                    this.msg = "Evaluation time:" + tt + '\r' + '\n' + "evaluated hall:" + appries.get("dating") + '\r' + '\n' + "evaluated window:" + appries.get("window") + '\r' + '\n' + "evaluated employee:" + appries.get("Name") + '\r' + '\n' + " unsatisfied description " + appries.get("keyname") + '\r' + '\n';
                }
                if (host != null) {
                    for (int l = 0; l < host.size(); ++l) {
                    	Map map = (Map)host.get(l);
                    	final String ip = map.get("ext2").toString();
                        //final String ip = host.get(l).get("ext2").toString();
                        final String[] sip = ip.split(",");
                        for (int m = 0; m < sip.length; ++m) {
                            temp.put(sip[m], sip[m]);
                            AppriesAction.logger.debug((Object)("\u7ba1\u7406Ip:" + sip[m]));
                        }
                    }
                }
                for (final Object key : temp.keySet()) {
                    final String mb = temp.get(key).toString();
                    if (!mb.equals("")) {
                        udp.send(mb, this.clientPort, this.msg);
                        AppriesAction.logger.debug((Object)("\u53d1\u9001" + temp.get(key).toString() + "\u6210\u529f"));
                    }
                }
                final List mobile = this.managerService.getuserMobile();
                final List l_m = new ArrayList();
                if (mobile != null) {
                    for (int i2 = 0; i2 < mobile.size(); ++i2) {
                    	Map map = (Map)mobile.get(i2);
                    	final String mobiles = map.get("ext1").toString();
                        //final String mobiles = mobile.get(i2).get("ext1").toString();
                        final String[] smobile = mobiles.split(",");
                        for (int j2 = 0; j2 < smobile.length; ++j2) {
                            l_m.add(smobile[j2]);
                            AppriesAction.logger.debug((Object)("\u53d1\u9001" + smobile[j2] + "\u6210\u529f"));
                        }
                    }
                }
                String s_m = "";
                for (int i3 = 0; i3 < l_m.size(); ++i3) {
                    s_m = String.valueOf(s_m) + l_m.get(i3).toString() + ",";
                }
                if (s_m.endsWith(",")) {
                    s_m = s_m.substring(0, s_m.length() - 1);
                }
                if (this.mobileIp.equals("")) {
                    final InetAddress addr = InetAddress.getLocalHost();
                    this.mobileIp = addr.getHostAddress();
                }
                final String t = String.valueOf(this.msg) + "||" + s_m;
                udp.send(this.mobileIp, this.mobilePort, String.valueOf(this.msg) + "||" + s_m);
                udp.close();
            }
            catch (Exception e3) {
                e3.printStackTrace();
                AppriesAction.logger.debug((Object)"\u83b7\u53d6\u9519\u8bef\u8bc4\u4ef7\u4fe1\u606f\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\u5458\u5de5\u5361\u53f7\u662f\u5426\u5b58\u5728\u91cd\u590d\u3002\u90e8\u95e8\u4fe1\u606f\u662f\u5426\u6b63\u786e\uff0cmac\u8bbe\u7f6e\u662f\u5426\u771f\u786e");
            }
        }
        AppriesAction.logger.debug((Object)"\u5f00\u59cb\u5199\u5165\u8bc4\u4ef7\u6570\u636e");
        if (!sam.equals("") && !eam.equals("") && !spm.equals("") && !epm.equals("")) {
            AppriesAction.logger.debug((Object)"\u5224\u65ad\u4e0a\u4e0b\u73ed\u65f6\u95f4\u4e0d\u4e3a\u7a7a\uff0ctrue");
            AppriesAction.logger.debug((Object)("\u5f53\u524d\u670d\u52a1\u5668\u65f6\u95f4\uff1a" + time));
            AppriesAction.logger.debug((Object)("\u5f53\u524d\u662f\u5426\u4e3a\u4e0a\u73ed\u65f6\u95f4:" + ((time.compareTo(sam) >= 0 && time.compareTo(eam) <= 0) || (time.compareTo(spm) >= 0 && time.compareTo(epm) <= 0))));
            if ((time.compareTo(sam) >= 0 && time.compareTo(eam) <= 0) || (time.compareTo(spm) >= 0 && time.compareTo(epm) <= 0)) {
                AppriesAction.logger.debug((Object)"\u5224\u65ad\u662f\u5426\u4e3a\u4e0a\u73ed\u65f6\u95f4\uff0ctrue");
                AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo));
                if (this.appriesService.addArrires3(mac, tt, cardnum, pj, demo, videofile, businessTime, min, sec)) {
                    AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo + "\u6210\u529f"));
                    this.msg = "success";
                }
                else {
                    AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo + "\u5931\u8d25"));
                    AppriesAction.logger.debug((Object)"\u5f00\u59cb\u5199\u5165\u9519\u8bef\u7684\u8bc4\u4ef7\u6570\u636e��������");
                    final boolean flag = this.errorInfoService.add(mac, cardnum, pj);
                    AppriesAction.logger.debug((Object)("\u5199\u5165\u9519\u8bef\u7684\u8bc4\u4ef7\u6570\u636e\u7ed3\u679c\u4e3a:" + flag));
                    this.msg = "error";
                }
            }
            else {
                AppriesAction.logger.debug((Object)"\u5f53\u524d\u4e3a\u4e0b\u73ed\u65f6\u95f4\u65e0\u6cd5\u5199\u5165\u6570\u636e");
                this.msg = "error";
            }
        }
        else {
            AppriesAction.logger.debug((Object)"\u4e0a\u4e0b\u73ed\u65f6\u95f4\u8bbe\u7f6e\u6709\u95ee\u9898\uff0c\u8bf7\u8bbe\u7f6e\u4e0b");
            this.msg = "error";
        }
        return "success";
    }
    
    public String appriesDel() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List deptList = this.deptService.findChildALL(deptIdGroup);
        if (deptList != null && deptList.size() > 0) {
            final Map dept = (Map) deptList.get(0);
            dept.put("fatherId", -1);
        }
        final List keyList = this.keyTypeService.findByApprieserId(1, 1);
        request.setAttribute("keyList", (Object)keyList);
        request.setAttribute("deptList", (Object)deptList);
        return "success";
    }
    
    public String appriesDelDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String url = request.getQueryString();
        String deptIds = request.getParameter("deptIds");
        if (deptIds == null || deptIds.equals("")) {
            final Map manager = (Map)request.getSession().getAttribute("manager");
            final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
            final Map power = this.powerService.getUserGroup(groupid);
            final String deptIdGroup = power.get("deptIdGroup").toString();
            deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        }
        String keys = request.getParameter("keys");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (deptIds.endsWith(",")) {
            deptIds = deptIds.substring(0, deptIds.length() - 1);
        }
        if (keys.endsWith(",")) {
            keys = keys.substring(0, keys.length() - 1);
        }
        final int rowsCount = this.queryService.countQueryZh(this.id, keys, deptIds, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", AppriesAction.pageSize, rowsCount, request, this);
        final List tempList = this.queryService.queryZh(this.id, keys, deptIds, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(tempList);
        final List chatList = this.queryService.QueryZhChat(this.id, keys, deptIds, this.startDate, this.endDate);
        request.setAttribute("chatList", (Object)chatList);
        request.setAttribute("url", (Object)url);
        return "success";
    }
    
    public String appriesDelDealDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String tempIds = request.getParameter("ids");
        int delnum = 0;
        if (!tempIds.equals("")) {
            delnum = this.appriesService.del(tempIds);
        }
        request.setAttribute("msg", (Object)this.getText("sundyn.deleteSuccess"));
        return "success";
    }
    
    public String appriesDelDealDelAll() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String url = request.getQueryString();
        String deptIds = request.getParameter("deptIds");
        if (deptIds == null || deptIds.equals("")) {
            final Map manager = (Map)request.getSession().getAttribute("manager");
            final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
            final Map power = this.powerService.getUserGroup(groupid);
            final String deptIdGroup = power.get("deptIdGroup").toString();
            deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        }
        String keys = request.getParameter("keys");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (deptIds.endsWith(",")) {
            deptIds = deptIds.substring(0, deptIds.length() - 1);
        }
        if (keys.endsWith(",")) {
            keys = keys.substring(0, keys.length() - 1);
        }
        final int delnum = this.appriesService.del(this.id, keys, deptIds, this.startDate, this.endDate);
        request.setAttribute("msg", (Object)this.getText("sundyn.deleteSuccess"));
        return "success";
    }
    
    public String appriesDebug() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final List keyList = this.keyTypeService.findByApprieserId(1, 1);
        final List employeeList = this.employeeService.findAllEmployee();
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date = df.format(new java.util.Date());
        final List allMac = this.appriesService.getAllMac();
        request.setAttribute("employeeList", (Object)employeeList);
        request.setAttribute("date", (Object)date);
        request.setAttribute("allMac", (Object)allMac);
        request.setAttribute("keyList", (Object)keyList);
        request.setAttribute("bussinessTime", (Object)"66");
        request.setAttribute("tel", (Object)"1234567");
        request.setAttribute("idCard", (Object)"7654321");
        return "success";
    }
    
    public String appriesAddDebug() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String tt = request.getParameter("appriesDate");
        final String mac = request.getParameter("mac");
        final String cardNum = request.getParameter("cardNum");
        final String appriesButton = request.getParameter("appriesButton");
        String businessTime = request.getParameter("bussinessTime");
        final String tel = request.getParameter("tel");
        final String idCard = request.getParameter("idCard");
        int min = 0;
        int sec = 0;
        if (businessTime != null) {
            min = Integer.valueOf(businessTime) / 60;
            sec = Integer.valueOf(businessTime) % 60;
            businessTime = String.valueOf(String.valueOf(min)) + "\u5206" + String.valueOf(sec) + "\u79d2";
        }
        final String url = "http://localhost/appriesAddSp.action?mac=" + mac + "&&tt=" + tt + "&&cardnum=" + cardNum + "&&pj=" + appriesButton + "&&tel=" + tel + "&&idCard=" + idCard + "&&businessType=1";

        request.setAttribute("msg", (Object)url);
        boolean b = this.appriesService.addArriresXiangYang(mac, tt, cardNum, appriesButton, "13333", "", businessTime, 0, 0, tel, idCard, null, null, null, null, null, null);
        System.out.println("debug\u6dfb\u52a0\u8bc4\u4ef7\u6570\u636e\u8bf7\u6c42=" + url + ", 结果：" + b);
        return "success";
    }
    
    public String getWeather() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String macid = request.getParameter("mac");
        if (macid == null || macid.equals("")) {
            this.msg = "mac\u503c\u4e0d\u80fd\u4e3a\u7a7a";
            this.str = "mac\u503c\u4e0d\u80fd\u4e3a\u7a7a";
            return "success";
        }
        int cityid = 0;
        final Map m = this.deptService.findByMac(macid);
        try {
            final boolean b = m.isEmpty();
        }
        catch (Exception e) {
            this.msg = "mac\uff1a" + macid + "\u6ca1\u6709\u7ed1\u5b9a\u8bbe\u5907.";
            this.str = "mac\uff1a" + macid + "\u6ca1\u6709\u7ed1\u5b9a\u8bbe\u5907.";
            return "success";
        }
        cityid = (int) m.get("cityid");
        cityid = ((cityid == 0) ? 1 : cityid);
        this.city = this.cityutils.getCityById(cityid);
        final String st = "http://m.weather.com.cn/data/" + this.city.getCitynum() + ".html";
        this.str = GetWeatherString.getPageContent(st, "", 2000);
        for (int i = 0; i < 2 && (this.str == null || this.str.equals("")); ++i) {
            this.str = GetWeatherString.getPageContent("http://m.weather.com.cn/data/" + this.city.getCitynum() + ".html", "", 2000);
        }
        return "success";
    }
    
    public String macBinding() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String macid = request.getParameter("mac");
        if (macid == null || macid.equals("")) {
            this.msg = "mac\u503c\u4e0d\u80fd\u4e3a\u7a7a";
            this.str = "mac\u503c\u4e0d\u80fd\u4e3a\u7a7a";
            return "success";
        }
        final Map m = this.deptService.findByMac(macid);
        if (m == null) {
            this.msg = "mac\uff1a" + macid + "\u53ef\u4ee5\u4f7f\u7528";
            this.str = "mac\uff1a" + macid + "\u53ef\u4ee5\u4f7f\u7528";
        }
        else {
            this.msg = "mac \u5df2\u7ecf\u88ab\u5360\u7528";
            this.str = "mac \u5df2\u7ecf\u88ab\u5360\u7528";
        }
        request.setAttribute("json", (Object)this.msg);
        return "success";
    }
    
    public static Logger getLogger() {
        return AppriesAction.logger;
    }
    
    public static long getSerialversionuid() {
        return 1L;
    }
    
    public static Integer getPagesize() {
        return AppriesAction.pageSize;
    }
    
    public String getStr() {
        return this.str;
    }
    
    public void setStr(final String str) {
        this.str = str;
    }
    
    public City getCity() {
        return this.city;
    }
    
    public void setCity(final City city) {
        this.city = city;
    }
    
    public Province getProvince() {
        return this.province;
    }
    
    public void setProvince(final Province province) {
        this.province = province;
    }
    
    public CitysUtils getCityutils() {
        return this.cityutils;
    }
    
    public void setCityutils(final CitysUtils cityutils) {
        this.cityutils = cityutils;
    }
}
