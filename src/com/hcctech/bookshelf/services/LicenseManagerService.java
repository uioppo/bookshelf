package com.hcctech.bookshelf.services;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsLicenseBatch;
import com.hcctech.bookshelf.pojo.BsLicenseKey;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.pojo.BsSchool;

/**
 * 后台授权码管理
 * @author apple
 */
public interface LicenseManagerService {
	/**
	 * 授权码管理     批次列表
	 * @param batch 批次号 （搜索）
	 * @param schoolName  学校 （搜索）
	 * @param pageNo 当前页数
	 * @param pageSize 每页显示条数
	 * @param sort 排序字段
	 * @param order 排序类型
	 * @return List<BsLicenseBatch> list
	 */
	@Transactional(readOnly=true)
	Page<BsLicenseBatch> loadBatchList(String batch,String schoolName, int pageNo, int pageSize, String sort, String order);
	/**
	 * 授权码管理     批次详细
	 * @param batch 批次号 （搜索）
	 * @return List<BsLicenseKey> list
	 */
	@Transactional(readOnly=true)
	Map<String,Object> loadKeyByBatch(String batch);
	/**
	 * 添加授权码     查询所有商品
	 * @param order 排序类型
	 * @param sort  排序字段
	 * @param pageSize 每页条数
	 * @param pageNo 当前页
	 * @return Page<BsProducts> page
	 */
	@Transactional(readOnly=true)
	Page<BsProducts> loadProductAll(String productName, int pageNo, int pageSize, String sort, String order,String idStr);
	/**
	 * 添加授权码     查询区域
	 * @param shengId 省节点ID
	 * @return List<BsArea> list
	 */
	@Transactional(readOnly=true)
	List<BsArea> loadSheng(String shengId);
	/**
	 * 添加授权码     提交到数据库
	 * @param idStr 电子书ID
	 * @param quId 地区ID
	 * @param schoolId  学校ID
	 * @param schoolStage  学段
	 * @param keySize   授权码数量 
	 * @param lifeTime  使用期限
	 * @param pattern 销售渠道 
	 * @return 
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void addLicenseKeyBatch(String idStr, String quId, Integer schoolId,
			String schoolStage, Integer keySize, String lifeTime, String pattern,String serVersion);
	/**
	 * 授权码管理     导出授权码
	 * @param batch 批次号 （搜索）
	 * @throws Exception 
	 */
	@Transactional(readOnly=true)
	void exportExcel(String batch) throws Exception;
	/**
	 * 授权码管理     查询学校
	 * @param shengId 地区ID
	 *  @return List<BsSchool> list
	 */
	@Transactional(readOnly=true)
	List<BsSchool> loadSchool(String shengId);
	
	@Transactional(readOnly=true)
	BsLicenseKey loadByAuthCode(String authCode);
}
