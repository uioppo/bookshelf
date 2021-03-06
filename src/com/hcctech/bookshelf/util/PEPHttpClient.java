package com.hcctech.bookshelf.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.RegisterService;

public class PEPHttpClient {

	// 接口地址
	private static Log logger = LogFactory.getLog(PEPHttpClient.class);
	private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";
	private static final String AUTHKEY = "821f03288846297c2cf43c34766a38f7";
	private static final byte[] lock = new byte[0];
	private static PEPHttpClient instance = null;
	private static HttpClient httpClient = null;

	/**
	 * 接口地址
	 * 
	 * @param url
	 * @throws Exception 
	 */
	private PEPHttpClient() {
	    HttpParams params = new BasicHttpParams();
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	    HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		httpClient = new DefaultHttpClient(params);
	}
	
	public static PEPHttpClient getInstance() {
	    if(instance == null) {
	        synchronized(lock)
            {
                if(instance == null) {
                    instance = new PEPHttpClient();
                }
            }
	    }
	    return instance;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
	    return instance;
	}
	
	public static void main(String[] args)
    {
//        TempRet str = PEPHttpClient.getInstance().post(DomainUtil.getPepPlatformUrl(),"{\"authKey\":\"821f03288846297c2cf43c34766a38f7\",\"pepact\":\"reg\",\"uname\":\"test_yangxf\",\"email\":\"test_yangxf@pep.com.cn\",\"passwd\":\"123,45678\"}");
//	    BsWebUser user = new BsWebUser();
//	    user.setWuEmail("zhaiyb@pep.com.cn");
//	    user.setWuPassword("qwe123fgh");
//	    user.setWuUserName("test_yangxf");
//	    
//        System.out.println(StringEscapeUtils.unescapeJava(PEPHttpClient.getInstance().registerToPep(user)));
	    System.out.println(StringEscapeUtils.unescapeJava(String.valueOf(PEPHttpClient.getInstance().chkUserPasswd(null, "zhaiyb@pep.com.cn", "qwe123fgh"))));
    }
	
	public String registerToPep(BsWebUser bsWebUser) {
	    Map parameters = new HashMap();
	    
	    parameters.put("authKey", AUTHKEY);
	    parameters.put("pepact", "reg");
	    parameters.put("email", bsWebUser.getWuEmail());
	    parameters.put("uname", bsWebUser.getWuUserName());
	    parameters.put("passwd", bsWebUser.getWuPassword());
	    if(bsWebUser.getBsUserInfo()!=null) {
	        parameters.put("nickname", bsWebUser.getBsUserInfo().getNickName());
	        parameters.put("realName", bsWebUser.getBsUserInfo().getRealName());
	        parameters.put("mobilephone", bsWebUser.getBsUserInfo().getMobile());
	    }
	    
	    JSONObject obj2 = post(DomainUtil.getPepPlatformUrl(),JSONValue.toJSONString(parameters));
        if(obj2.get("errno").toString().equals("1001")) {//成功
	        return "0";
	    }else if(obj2.get("errno").toString().equals("1002") || obj2.get("errno").toString().equals("1003")){
	        return "502";
	    }
        System.out.println(obj2.get("errmsg").toString());
	    return obj2.get("errno").toString();
	}
	
	public int chkUserPasswd(String uname,String email,String passwd) {
        Map parameters = new HashMap();
        
        parameters.put("authKey", AUTHKEY);
        parameters.put("pepact", "chkUserPasswd");
        parameters.put("email", email);
        parameters.put("uname", uname);
        parameters.put("passwd",passwd);
        JSONObject obj2 =  post(DomainUtil.getPepPlatformUrl(),JSONValue.toJSONString(parameters));
        if(obj2.get("errno").toString().equals("1320")) {//成功
            return Integer.valueOf(obj2.get("userId").toString());
        }
        return -1;
    }
	
	public BsWebUser createUser(int pepUserId,String pwd,RegisterService registerService) {
	    //得到人教社用户信息
	    BsWebUser bsWebUser  = null;
        Map parameters = new HashMap();
        parameters.put("authKey", AUTHKEY);
        parameters.put("pepact", "info");
        parameters.put("userId", pepUserId);
        JSONObject obj2 =  post(DomainUtil.getPepPlatformUrl(),JSONValue.toJSONString(parameters));
        if(obj2.get("errno").toString().equals("1701")) {//成功
            //保存到数据库
            bsWebUser = new BsWebUser();
            bsWebUser.setWuPassword(pwd);
            bsWebUser.setWuRegTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            bsWebUser.setLastLogin(bsWebUser.getWuRegTime() );
            bsWebUser.setWuActivestatus(Integer.valueOf(obj2.get("email_activity").toString()));
            bsWebUser.setWuUserName(obj2.get("uname").toString());
            bsWebUser.setWuEmail(obj2.get("email").toString());
            BsUserInfo info = new BsUserInfo();
            info.setNickName(obj2.get("nickName").toString());
            info.setRealName(obj2.get("realName").toString());
            info.setMobile(obj2.get("mobilephone").toString());
            bsWebUser.setBsUserInfo(info);
            bsWebUser.setWuActivestatus(1) ;
            registerService.registerWebUser(bsWebUser);
        }
        return bsWebUser;
    }
	
	
	public boolean update(String email,String nickName,String realName ,String mobilephone) {
        Map parameters = new HashMap();
        
        parameters.put("authKey", AUTHKEY);
        parameters.put("pepact", "updateByEmail");
        parameters.put("email", email);
        if(nickName!=null && !nickName.equals(""))
        parameters.put("nickName", nickName);
        if(realName!=null && !realName.equals(""))
        parameters.put("realName", realName);
        if(mobilephone!=null && !mobilephone.equals(""))
        parameters.put("mobilephone",mobilephone);
        JSONObject obj2 = post(DomainUtil.getPepPlatformUrl(),JSONValue.toJSONString(parameters));
        if(obj2.get("errno").toString().equals("1101")) {//成功
            return true;
        }
        return false;
    }
	
	public boolean updatePwd(String email,String passwd,String oldPwd) {
        Map parameters = new HashMap();
        
        parameters.put("authKey", AUTHKEY);
        parameters.put("pepact", "updatePasswdByEmail");
        parameters.put("email", email);
        if(passwd==null || passwd.equals("") || oldPwd==null || oldPwd.equals(""))
            return false;
        parameters.put("passwd",oldPwd );
        parameters.put("passwdNew",passwd );
        JSONObject obj2 = post(DomainUtil.getPepPlatformUrl(),JSONValue.toJSONString(parameters));
        if(obj2.get("errno").toString().equals("1101")) {//成功
            return true;
        }
        return false;
    }
	/**
	 * 调用 API
	 * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
	 * @param parameters
	 * @return
	 */
	private JSONObject post(String url ,String parameters) {
	    HttpPost method = new HttpPost(url);
		String body = null;
//		logger.info("parameters:" + parameters);
		int status = 0;
		if (method != null && parameters != null && !"".equals(parameters.trim())) {
			try {
				// 添加参数
//				method.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				method.setEntity(new StringEntity(parameters, HTTP.UTF_8));
				long startTime = System.currentTimeMillis();

				// 设置编码
				HttpResponse response = httpClient.execute(method);
				long endTime = System.currentTimeMillis();
				int statusCode = response.getStatusLine().getStatusCode();
				logger.info("statusCode:" + statusCode);
				logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("Method failed:" + response.getStatusLine());
					status = 1;
					body = "{\"errno\":1,\"errmsg\":\"连接出错\"}";
				}

				// Read the response body
				body = EntityUtils.toString(response.getEntity());

			} catch (IOException e) {
				// 发生网络异常
				logger.error("exception occurred!\n" + ExceptionUtils.getFullStackTrace(e));
				// 网络错误
				status = 3;
				body = "{\"errno\":3,\"errmsg\":\"网络异常\"}";
			} finally {
				logger.info("调用接口状态：" + status);
				method.abort();
			}

		}
//		JSONObject obj2=(JSONObject)JSONValue.parse(body);
//		return new TempRet(obj2.get("errno").toString(),obj2.get("errmsg").toString());
//		return body;
		System.out.println(parameters+"|"+StringEscapeUtils.unescapeJava(body));
		return (JSONObject)JSONValue.parse(body);
	}
	
	class TempRet{
	    private String errno;
	    private String errmsg;
	    
	    public TempRet(String errno,String errmsg) {
	        this.errno = errno;
	        this.errmsg = errmsg;
	    }
	    
	    @Override
	    public String toString()
	    {
	        // TODO Auto-generated method stub
	        return "{\"errno\":"+errno+",\"errmsg\":\""+errmsg+"\"}";
	    }
        /**
         * 获取 errno
         * 
         */
        public String getErrno()
        {
            return errno;
        }
        /**
         * 设置 errno
         * 
         */
        public void setErrno(String errno)
        {
            this.errno = errno;
        }
        /**
         * 获取 errmsg
         * 
         */
        public String getErrmsg()
        {
            return errmsg;
        }
        /**
         * 设置 errmsg
         * 
         */
        public void setErrmsg(String errmsg)
        {
            this.errmsg = errmsg;
        }
	    
	}
}
