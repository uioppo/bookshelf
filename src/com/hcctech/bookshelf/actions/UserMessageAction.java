package com.hcctech.bookshelf.actions;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.struts2.ServletActionContext;


import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsSchool;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.UserMessageService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 用户管理
 * @author yong
 *
 */
public class UserMessageAction extends ActionSupport {

	private static final long serialVersionUID = -4570739705281099901L;

	private UserMessageService userMessageServiceImpl;

	private BsWebUser user;

	private List<BsArea> listArea;

	private List<BsArea> listAreaplus;
	
	private Integer areaid;
	
	private Integer shengId;//查询学校的市ID 或县ID
	
	private List<BsSchool> listSchool;
	
	public String showSchool(){
		listSchool=userMessageServiceImpl.findSchoolList(shengId);
		return SUCCESS;
	}
	
	/**
	 * 重找一个登陆的用户
	 * @return
	 */
	public String findUserName() {
		user = (BsWebUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user!=null&&user.getWuId()!=null){
			user=userMessageServiceImpl.loadMsgById(user.getWuId());
			if(user!=null){
				return "message";
			}else{
				return "login";
			}
		}else{
			return "login";
		}
	}
	/**
	 * 更新用户信息
	 * @return
	 */
	public String updateUser() {
		user.getBsUserInfo().setRealName(user.getBsUserInfo().getRealName());
		user.getBsUserInfo().setNickName(user.getBsUserInfo().getNickName());
		user.getBsUserInfo().setMobile(user.getBsUserInfo().getMobile());
		user.getBsUserInfo().setBirthday(user.getBsUserInfo().getBirthday());
		user.getBsUserInfo().setSchool(user.getBsUserInfo().getSchool());
		user.getBsUserInfo().setSheng(user.getBsUserInfo().getSheng());
		user.getBsUserInfo().setSex(user.getBsUserInfo().getSex());
		//user.getBsUserInfo().setAge(user.getBsUserInfo().getAge());
		if(user.getBsUserInfo().getBirthday()!=null){
			Date dd=user.getBsUserInfo().getBirthday();
			Date today=new Date(); 
			Calendar calendar = GregorianCalendar.getInstance(); 
			Calendar calendar1 = GregorianCalendar.getInstance(); 
			calendar.setTime(dd);
			calendar1.setTime(today);
			user.getBsUserInfo().setAge(calendar1.get(Calendar.YEAR)-calendar.get(Calendar.YEAR));
		}
		user.getBsUserInfo().setShi(user.getBsUserInfo().getShi());
		user.getBsUserInfo().setXian(user.getBsUserInfo().getXian());
		userMessageServiceImpl.update(user.getBsUserInfo());
		return "list";
	}
	/**
	 * 省份列表
	 * @return
	 */
	public String showArea() {
		listArea = userMessageServiceImpl.findAllArea();
		return SUCCESS;
	}
	/**
	 * 城镇列表
	 * @return
	 */
	public String showAreaPlus() {
		listAreaplus = userMessageServiceImpl.findAllAreaPlus(areaid);
		return SUCCESS;
	}

	public BsWebUser getUser() {
		return user;
	}

	public void setUser(BsWebUser user) {
		this.user = user;
	}

	public void setUserMessageServiceImpl(
			UserMessageService userMessageServiceImpl) {
		this.userMessageServiceImpl = userMessageServiceImpl;

	}

	public List<BsArea> getListArea() {
		return listArea;
	}

	public void setListArea(List<BsArea> listArea) {
		this.listArea = listArea;
	}

	public List<BsArea> getListAreaplus() {
		return listAreaplus;
	}

	public void setListAreaplus(List<BsArea> listAreaplus) {
		this.listAreaplus = listAreaplus;
	}

	public Integer getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	public List<BsSchool> getListSchool() {
		return listSchool;
	}

	public void setShengId(Integer shengId) {
		this.shengId = shengId;
	}


}