package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*
 *  Demo ：对数据库SQlite的使用
 *  author: 顾修忠
 *  QQ:893115871
 *  Email:gfj19900401@163.com
 * 
 * */
public class MySqliteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "stu_db";// 数据库的名字
	private static final int DATABASEVERSION = 1;// 版本号
	private static final String TABLE_NAME = "stu_table";// 表名

	private SQLiteDatabase db;// 数据库
	private static final String TAG = "MyDataBase";

	// 4个字段
	public static final String ID = "_id";
	public static final String stuName = "stu_name";
	public static final String stuNumber = "stu_number";
	public static final String stuScore = "stu_score";

	public MySqliteHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASEVERSION);
		// 打开或新建数据库(第一次时创建)获得SQLiteDatabase对象，为了读取和写入数据
		db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreate()");
		// 创建表的sql语句
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + stuName + " TEXT,"
				+ stuNumber + " INTEGER," + stuScore + " FLOAT)";
		db.execSQL(sql);

	}
   //更新数据库
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(TAG, " onUpgrade() ");
		// 删除表的SQL
		String sql = "DROP TABLE IF EXITS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
   //关闭数据库
	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		Log.i(TAG, "close()");
		db.close();
		super.close();
	}
	
	// 查询所有的数据 ，返回Cursor对象(按照id的升序排列)
	public Cursor searchAllData()
	{
		Log.i(TAG, " searchAllData()");
		//asc是升序desc为降序（默认为asc）
		return db.query(TABLE_NAME, null, null, null, null, null,MySqliteHelper.ID+" ASC" );
	}
	//插入数据
	public void  insertData(String name,int number,float score )
	{
		ContentValues values=new ContentValues();
		values.put(MySqliteHelper.stuName, name);
		values.put(MySqliteHelper.stuNumber, number);
		values.put(MySqliteHelper.stuScore, score);
		long row=db.insert(TABLE_NAME, null, values);
		Log.i(TAG, "insertData row="+row);
	}
	//查询指定的信息
	public Cursor searchSpecific(int number)
	{
		 String[] columns={
				 MySqliteHelper.ID,
				 MySqliteHelper.stuName,
				 MySqliteHelper.stuNumber,
				 MySqliteHelper.stuScore
		 };
		
		Cursor cur=db.query(TABLE_NAME, columns,  MySqliteHelper.stuNumber+"="+number, null, null, null, null);
	    Log.i("searchSpecific()", " cur.getCount()="+cur.getCount());
		return cur;
	}
	//修改数据
	public void upDateinfo(int id ,String name,int number,float score )
	{
		ContentValues values=new ContentValues();
		values.put(MySqliteHelper.stuName, name);
		values.put(MySqliteHelper.stuNumber, number);
		values.put(MySqliteHelper.stuScore, score);
		String whereClause=MySqliteHelper.ID+" = ? ";
		String whereArgs[]={Integer.toString(id)};
		
		int rowaffected =db.update(TABLE_NAME, values, whereClause, whereArgs);
		Log.i(TAG, "upDateinfo()  rowaffected="+rowaffected);
	}
	
	//删除数据
	public void deleteStuInfo(int id)
	{
		int rowaffected =db.delete(TABLE_NAME, MySqliteHelper.ID+"="+id, null);
		Log.i(TAG, "deleteStuInfo()  rowaffected="+rowaffected);
	}
	//判断是否存在改学生的信息
	public Cursor isHaveThisStu(int number)
	{
		 String[] columns={
				 MySqliteHelper.ID,
				 MySqliteHelper.stuName,
				 MySqliteHelper.stuNumber,
				 MySqliteHelper.stuScore
		 };
		
		Cursor cur=db.query(TABLE_NAME, columns,  MySqliteHelper.stuNumber+"="+number, null, null, null, null);
	    Log.i("isHaveThisStu()", " cur.getCount()="+cur.getCount());
		return cur;
	}
}
