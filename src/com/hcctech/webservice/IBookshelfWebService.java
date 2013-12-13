package com.hcctech.webservice;

import java.util.List;

public interface IBookshelfWebService
{
    public int downLoadValidate(int myBookId, String cpuIdStr);
    
    public List<String> download(int myBookId,String cpuIdStr,String deviceName);
    
    public String[] login(String username,String password); 
}
