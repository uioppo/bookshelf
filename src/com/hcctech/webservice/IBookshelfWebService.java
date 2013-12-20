package com.hcctech.webservice;

import java.util.List;

public interface IBookshelfWebService
{
    public int downLoadValidate(String username,String password,int myBookId, String cpuIdStr);
    
    public List<String> download(String username,String password,int myBookId,String cpuIdStr,String deviceName);
    
    public String[] login(String username,String password); 
}
