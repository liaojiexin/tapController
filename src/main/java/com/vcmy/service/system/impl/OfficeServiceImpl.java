package com.vcmy.service.system.impl;

import com.vcmy.dao.OfficeDao;
import com.vcmy.entity.Office;
import com.vcmy.service.system.OfficeService;
import com.vcmy.util.TreeUtils;
import com.vcmy.util.WebContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现层
 * 
 * @author yuhaibing
 * @date 2020-04-15 15:55:25
 */
@Service
public class OfficeServiceImpl implements OfficeService {

	@Autowired
	private OfficeDao officeDao;

//	@Override
//	public Integer add(Office office) {
//		office.setCreateBy(WebContextUtils.getName());
//		return this.officeMapper.insertOffice(office);
//	}
//
//	@Override
//	public Integer delete(Long officeId) {
//		return officeMapper.deleteOfficeById(officeId);
//	}
//
//	@Override
//	public Integer update(Office office) {
//		if (this.officeMapper.selectOfficeById(office.getOfficeId()) == null) {
//			return 0;
//		}
//		office.setUpdateBy(WebContextUtils.getName());
//		if(office.getParentId()!=null&&office.getParentId().equals(office.getOfficeId())){	//如果更新的父级id等于自己的id,不做改变
//			office.setParentId(officeMapper.selectOfficeById1(office.getOfficeId()));	//父级id还是原来那个id
//		}
//		return officeMapper.updateOffice(office);
//	}
//
//	@Override
//	public Integer remove(Long[] ids) {
//		return officeMapper.batchDeleteOffice(ids);
//	}
//
//	@Override
//	public List<Office> selectList(Office office) {
//		return officeMapper.findList(office);
//	}
//
//	@Override
//	public Office selectById(Long officeId) {
//		return officeMapper.selectOfficeById(officeId);
//	}

	@Override
	public List<Office> selectOfficeTreeList(Office office) {
		List<Office> list =officeDao.selectOffice(office);
		List<Office> officelist=TreeUtils.assembleTreeOffice(list);
		return officelist;
	}

//	@Override
//	public int selectCountByOfficeName(String officeName) {
//		int num=officeMapper.selectCountByOfficeName(officeName);
//		return num;
//	}
//
//	@Override
//	public void updateParentId(Long parentId) {
//		officeMapper.updateParentId(parentId);
//	}
//
//	@Override
//	public List<Office> selectAllOfficeByOfficeId(Long officeId, List<Office> listAll) {
//		//查出要删除部门下面的子部门信息
//		List<Office> officeList=officeMapper.selectOfficeByParentId(officeId);
//		if (officeList.size()>0){
//			for (Office office:officeList) {
//				listAll.add(office);
//				selectAllOfficeByOfficeId(office.getOfficeId(),listAll);
//			}
//		}
//		return listAll;
//	}
//
//	@Override
//	public List<Office> selectOfficeByOfficeId(Long officeId) {
//		List<Office> listAll=new ArrayList<>();
//		//先查出要删除的部门信息
//		Office o=officeMapper.selectOfficeById(officeId);
//		listAll.add(o);
//		selectAllOfficeByOfficeId(officeId,listAll);
//		return listAll;
//	}
}
