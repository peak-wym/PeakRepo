package com.example.sqlitedemo;

public class Until {
	
	/*
	 *  Demo ：对数据库SQlite的使用
	 *  author: 顾修忠
	 *  QQ:893115871
	 *  Email:gfj19900401@163.com
	 * 
	 * 
	 *
	 *  一系列的静态方法
	 * */
	 public static boolean  isSearch=false;//判断是否处于查询的表单的标志(flag)
	 public static boolean   isInsert=false;//判断是否处于添加的表单的标志(flag)
	 public static boolean    isUpdataOrDelete=false;//判断是否处于删除或更新的表单的标志(flag)
	 public static boolean     isDelete=false;//判断是否处于删除的表单的标志(flag)

	public static boolean isSearch() {
		return isSearch;
	}

	public static void setSearch(boolean isSearch) {
		Until.isSearch = isSearch;
	}

	public static boolean isInsert() {
		return isInsert;
	}

	public static void setInsert(boolean isInsert) {
		Until.isInsert = isInsert;
	}

	public static boolean isUpdataOrDelete() {
		return isUpdataOrDelete;
	}

	public static void setUpdataOrDelete(boolean isUpdataOrDelete) {
		Until.isUpdataOrDelete = isUpdataOrDelete;
	}

	public static boolean isDelete() {
		return isDelete;
	}

	public static void setDelete(boolean isDelete) {
		Until.isDelete = isDelete;
	}
	 

}
