package com.hcctech.bookshelf.listener;

import java.util.HashMap;

import flex.messaging.FlexSession;

public class MySessionContext {
	
	 private static MySessionContext instance;  
	    private HashMap<String, FlexSession> mymap;  
	    private MySessionContext()  
	    {  
	        mymap = new HashMap<String, FlexSession>();  
	    }  
	    public static MySessionContext getInstance()  
	    {  
	        if(instance==null)  
	        {  
	            instance = new MySessionContext();  
	        }  
	        return instance;  
	    }  
	    public synchronized void addSession(FlexSession session,String key)  
	    {  
	        if(session!=null)  
	        {  
	        	System.out.println("addSession:"+key);
	            mymap.put(key, session);  
	        }  
	    }  
	    public synchronized void delSession(FlexSession session)  
	    {
	        if(session!=null)  
	        {
	        	System.out.println("delSession:"+session.getId());
	        	if(session.getAttribute("userid")!=null&&!session.getAttribute("userid").equals(""))
	        		mymap.remove(session.getAttribute("userid"));
	        //	session.invalidate();
	        }
	    }
	    public synchronized FlexSession getSession(String session_id)  
	    {  
	        if(session_id==null)return null;
	        return mymap.get(session_id);  
	    }
}
