package com.example.sqlitedemo;

public class Until {
	
	/*
	 *  Demo �������ݿ�SQlite��ʹ��
	 *  author: ������
	 *  QQ:893115871
	 *  Email:gfj19900401@163.com
	 * 
	 * 
	 *
	 *  һϵ�еľ�̬����
	 * */
	 public static boolean  isSearch=false;//�ж��Ƿ��ڲ�ѯ�ı��ı�־(flag)
	 public static boolean   isInsert=false;//�ж��Ƿ�����ӵı��ı�־(flag)
	 public static boolean    isUpdataOrDelete=false;//�ж��Ƿ���ɾ������µı��ı�־(flag)
	 public static boolean     isDelete=false;//�ж��Ƿ���ɾ���ı��ı�־(flag)

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
