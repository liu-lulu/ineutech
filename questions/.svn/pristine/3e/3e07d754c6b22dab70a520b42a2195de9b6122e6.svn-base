package com.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.StudentDao;
import com.entity.Student;

public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfjq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@Override
	public Student login(String str_num) {
		String hql = " from Student t where t.str_num = '"+str_num+"' ";
		List<Student> list = this.find(hql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}	
	
}
