package com.hcctech.webservice;

import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.UserLoginService;

public class UserLoginServiceImpl implements IUserLoginService
{
    private  UserLoginService userLoginService;

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
        
//        session.setAttribute("user", bsWebUser);
        return new String[] {String.valueOf(bsWebUser.getWuId()),bsWebUser.getBsUserInfo().getNickName(),bsWebUser.getBsUserInfo().getRealName()};
    
    }

    public UserLoginService getUserLoginService()
    {
        return userLoginService;
    }

    public void setUserLoginService(UserLoginService userLoginService)
    {
        this.userLoginService = userLoginService;
    }
    
}
