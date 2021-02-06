package com.vcmy.service.system;

import com.vcmy.entity.Office;
import java.util.List;

/**
 * 服务实现层接口
 * 
 * @author yuhaibing
 * @date 2020-04-15 15:55:25
 */
public interface OfficeService {

//	 Integer add(Office office);
//
//	 Integer delete(Long officeId);
//
//	 Integer update(Office office);
//
//	 Integer remove(Long[] ids);
//
//	 List<Office> selectList(Office office);
//
//	 Office selectById(Long officeId);

	 List<Office> selectOfficeTreeList(Office office);

//	int selectCountByOfficeName(String officeName);
//
//	void updateParentId(Long parentId);
//
//    List<Office> selectAllOfficeByOfficeId(Long officeId, List<Office> listAll);
//
//	List<Office> selectOfficeByOfficeId(Long officeId);
}
