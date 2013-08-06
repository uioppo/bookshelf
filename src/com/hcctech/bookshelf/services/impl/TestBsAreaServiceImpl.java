package com.hcctech.bookshelf.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hcctech.bookshelf.dao.BsAreaDao;
import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.services.BsAreaService;
import com.hcctech.bookshelf.util.TreeJson;

/**
 * @author zoujunpeng
 *
 */
public class TestBsAreaServiceImpl {

	static ApplicationContext ctx = null;
	@BeforeClass
    public static void init()
    {
//		classpath:applicationContext.xml,classpath:com/hcctech/bookshelf/spring/*_bean.xml
        ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml","classpath:com/hcctech/bookshelf/spring/*_bean.xml");
    }
//	@Test
	public void testfindAreaAll(){
		BsArea bsArea = new BsArea();
		bsArea.setAreaName("北京");
		BsAreaService areaService = (BsAreaService)ctx.getBean("bsAreaService");
		List<TreeJson> result = areaService.findAreaAll(bsArea);
		Assert.assertNotNull(result);
		
	}
	
	@Test
	public void testfindAreaAllForDao() {
		BsAreaDao dao = (BsAreaDao)ctx.getBean("bsAreaDao");
//		String sql = "SELECT * FROM bs_area WHERE BS__area_id IS NULL AND area_name LIKE '%北京%'   ORDER BY area_id";
//		List result = dao.findBySql(sql);
		List result = null;
			try {
				result = dao.findByHql("from BsArea where bsArea.areaId is null and  areaName like '%"+new String("北京".getBytes(),"UTF8")+"%'");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size()>0);
	}
	
}