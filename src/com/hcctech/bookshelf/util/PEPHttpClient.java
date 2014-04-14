package com.hcctech.bookshelf.util;

import java.io.IOException;
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
import com.hcctech.bookshelf.pojo.BsWebUser;

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
        TempRet str = PEPHttpClient.getInstance().post(DomainUtil.getPepPlatformUrl(),"{\"authKey\":\"821f03288846297c2cf43c34766a38f7\",\"pepact\":\"reg\",\"uname\":\"test_yangxf\",\"email\":\"test_yangxf@pep.com.cn\",\"passwd\":\"123,45678\"}");
        System.out.println(StringEscapeUtils.unescapeJava(str.toString()));
    }
	
	public boolean registerToPep(BsWebUser bsWebUser) {
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
	    
	    TempRet ret = post(DomainUtil.getPepPlatformUrl(),JSONValue.toJSONString(parameters));
	    if(!ret.getErrno().equals("0")) {
	        return false;
	    }
	    return true;
	}
	/**
	 * 调用 API
	 * 0.成功 1.执行方法失败 2.协议错误 3.网络错误
	 * @param parameters
	 * @return
	 */
	private TempRet post(String url ,String parameters) {
	    HttpPost method = new HttpPost(url);
		String body = null;
		logger.info("parameters:" + parameters);
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
		JSONObject obj2=(JSONObject)JSONValue.parse(body);
		return new TempRet(obj2.get("errno").toString(),obj2.get("errmsg").toString());
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
