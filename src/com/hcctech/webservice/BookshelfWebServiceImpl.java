package com.hcctech.webservice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.ServiceGroupContext;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.MyBookDownLoadFlexService;
import com.hcctech.bookshelf.services.UserLoginService;
import com.hcctech.bookshelf.util.AESUtils;
import com.hcctech.bookshelf.util.Base64Utils;

public class BookshelfWebServiceImpl implements IBookshelfWebService
{
    
    private  UserLoginService userLoginService;
    
    private MyBookDownLoadFlexService myBookDownLoadFlexService;
    
    private BsWebUser user = null;
    
    public String[] login(String username, String password)
    {
        BsWebUser bsWebUser = userLoginService.login(username,password);
        if(bsWebUser == null) {
            //用户不存在
            return new String[] {"-1","用户名或密码错误"} ;
        }else {
            if(bsWebUser.getWuActivestatus() == 0) {
//              loginMsg = "该用户未激活！";
                return new String[] {"-1","该用户未激活！"} ;
            }else if(bsWebUser.getWuActivestatus() == 2){
//              loginMsg = "该用户已禁用！";
                return new String[] {"-1","该用户已禁用！"} ;
            }else if(bsWebUser.getWuActivestatus() == 3){
//              loginMsg = "该用户已锁定，请稍候再试！";
                return new String[] {"-1","该用户已锁定，请稍候再试！"} ;
            }
        }
        MessageContext context = MessageContext.getCurrentMessageContext();
        ServiceGroupContext ctx = context.getServiceGroupContext();
        ctx.setProperty("user",bsWebUser);
        System.out.println("login"+ctx.getProperties());
//        session.setAttribute("user", bsWebUser);
        return new String[] {String.valueOf(bsWebUser.getWuId()),bsWebUser.getBsUserInfo().getNickName(),bsWebUser.getBsUserInfo().getRealName()};
    
    }
    
    public int downLoadValidate(int myBookId, String cpuIdStr)
    {
        try {
            if(isLogin()) {
                int flag = myBookDownLoadFlexService.downLoadValidate(cpuIdStr, user, myBookId);
                return flag;
            }else {
                return 0;//登录不成功
            }
        }catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public List<String> download(int myBookId, String cpuIdStr, String deviceName)
    {
        try {
            if(isLogin()) {
                Map<String, String> map = myBookDownLoadFlexService.downloadEbook(cpuIdStr, user, myBookId,deviceName);
                if(map==null) {
                    return null;
                }
                StringBuffer buffer = new StringBuffer();
                buffer.append( map.get("path"));
                String str = cpuIdStr+"<,>"+user.getWuId()+"<,>"+myBookId;
                byte[] byt = null;
                String testxx = null;
                String secretStr =null;
                String key =  "JQy4loN+iEaybInzfBFGTw==";
                byt = AESUtils.encrypt(str.getBytes(), key);
                buffer.append("&kongfu=");
                String str11 = Base64Utils.encode(byt);
                testxx = str11;
                str11 = URLEncoder.encode(str11, "UTF-8");
                buffer.append(str11);
                
                
                //String secret = map.get("bookcode")+"-++-++-"+map.get("key")+"-++-++-"+map.get("deadline")+"-++-++-"+user.getWuEmail();
                StringBuffer secret = new StringBuffer();
                secret.append(map.get("bookcode"));
                secret.append("-++-++-");
                secret.append(map.get("key"));
                secret.append("-++-++-");
                secret.append(map.get("deadline"));
                secret.append("-++-++-");
                secret.append(user.getWuEmail());
                secret.append("-++-++-");
                secret.append(myBookId);
                secret.append("-++-++-");
                secret.append(cpuIdStr);
                
                String key1 = generateKey(myBookId, cpuIdStr, user);
                byte[] bs = AESUtils.encrypt(secret.toString().getBytes(),key1 );
                secretStr = Base64Utils.encode(bs);
                List<String> arr = new ArrayList<String>();
                arr.add(buffer.toString());
                arr.add(secretStr);
                arr.add(testxx);
                return arr;
            }else {
                return null;//登录不成功
            }
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String generateKey(int myBookId, String cpuIdStr, BsWebUser user)
            throws UnsupportedEncodingException, Exception {
        byte[] keyb = (cpuIdStr+myBookId+user.getWuEmail()).getBytes("UTF-8");
        byte[] kb = new byte[16];
        if(keyb.length>16){
            for (int i = 0; i < 8; i++) {
                kb[i] = keyb[i];
            }
            for (int i = keyb.length-1,j=8; i >  keyb.length-9; i--,j++) {
                kb[j] = keyb[i];
            }
        }
        return Base64Utils.encode(kb);
    }
    
    
    private boolean isLogin() {
        MessageContext context = MessageContext.getCurrentMessageContext();
        ServiceGroupContext ctx = context.getServiceGroupContext();
        System.out.println(ctx.getProperties());
        user = (BsWebUser)ctx.getProperty("user");
        if(user == null)
            return false;
        return true;
    }

    public UserLoginService getUserLoginService()
    {
        return userLoginService;
    }

    public void setUserLoginService(UserLoginService userLoginService)
    {
        this.userLoginService = userLoginService;
    }

    public MyBookDownLoadFlexService getMyBookDownLoadFlexService()
    {
        return myBookDownLoadFlexService;
    }

    public void setMyBookDownLoadFlexService(MyBookDownLoadFlexService myBookDownLoadFlexService)
    {
        this.myBookDownLoadFlexService = myBookDownLoadFlexService;
    }
    
}
