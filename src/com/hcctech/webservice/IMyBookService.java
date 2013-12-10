package com.hcctech.webservice;

import java.util.List;

public interface IMyBookService
{
    public int downLoadValidate(int myBookId, String cpuIdStr);
    
    public List<String> download(int myBookId,String cpuIdStr,String deviceName);
}
