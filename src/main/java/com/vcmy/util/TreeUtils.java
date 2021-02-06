package com.vcmy.util;

import com.vcmy.entity.Office;
import com.vcmy.entity.Permission;

import java.util.List;

public class TreeUtils {
	public static List<Office> assembleTreeOffice(List<Office> officeList) {
		if (officeList == null || officeList.size() <= 1) {
			return officeList;

		}
		for (int i = 0; i < officeList.size(); i++) {
			for (int j = i + 1; j < officeList.size(); j++) {
				if (officeList.get(i).getOfficeId().equals(officeList.get(j).getParentId())) {
					officeList.get(i).addChildren(officeList.get(j));
					officeList.get(j).setIsChild(true);
				} else if (officeList.get(j).getOfficeId().equals(officeList.get(i).getParentId())) {
					officeList.get(j).addChildren(officeList.get(i));
					officeList.get(i).setIsChild(true);
				}
			}
		}
		Integer listSize = officeList.size();
		for (int i = 0; i < listSize; i++) {
			if (officeList.get(i).getIsChild()) {
				officeList.remove(i);
				i--;
				listSize--;
			}
		}
		return officeList;
	}

	public static List<Permission> assembleTreePermission(List<Permission> permissionList) {
		if (permissionList == null || permissionList.size() <= 1) {
			return permissionList;
		}
		for (int i = 0; i < permissionList.size(); i++) {
			for (int j = i + 1; j < permissionList.size(); j++) {
				if (permissionList.get(i).getPermissionId().equals(permissionList.get(j).getParentId())) {
					permissionList.get(i).addChildren(permissionList.get(j));
					permissionList.get(j).setIsChild(true);
				} else if (permissionList.get(j).getPermissionId().equals(permissionList.get(i).getParentId())) {
					permissionList.get(j).addChildren(permissionList.get(i));
					permissionList.get(i).setIsChild(true);
				}
			}
		}
		Integer listSize = permissionList.size();
		for (int i = 0; i < listSize; i++) {
			if (permissionList.get(i).getIsChild()) {
				permissionList.remove(i);
				i--;
				listSize--;
			}
		}
		return permissionList;
	}
}
