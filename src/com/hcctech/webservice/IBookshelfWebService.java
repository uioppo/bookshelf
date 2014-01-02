package com.hcctech.webservice;

import java.util.List;

public interface IBookshelfWebService
{
    public int downLoadValidate(String username,String password,int myBookId, String cpuIdStr,String md5);
    
    public List<String> download(String username,String password,int myBookId,String cpuIdStr,String deviceName,String md5);
    
    public String[] login(String username,String password,String md5);
    
    public List<String> getDownLoadInfo(String username,int bookId,String cpuIdStr,String deviceName,String md5);
}
