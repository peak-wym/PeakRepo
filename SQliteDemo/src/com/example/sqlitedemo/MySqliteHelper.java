package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*
 *  Demo �������ݿ�SQlite��ʹ��
 *  author: ������
 *  QQ:893115871
 *  Email:gfj19900401@163.com
 * 
 * */
public class MySqliteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "stu_db";// ���ݿ������
	private static final int DATABASEVERSION = 1;// �汾��
	private static final String TABLE_NAME = "stu_table";// ����

	private SQLiteDatabase db;// ���ݿ�
	private static final String TAG = "MyDataBase";

	// 4���ֶ�
	public static final String ID = "_id";
	public static final String stuName = "stu_name";
	public static final String stuNumber = "stu_number";
	public static final String stuScore = "stu_score";

	public MySqliteHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASEVERSION);
		// �򿪻��½����ݿ�(��һ��ʱ����)���SQLiteDatabase����Ϊ�˶�ȡ��д������
		db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCreate()");
		// �������sql���
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + stuName + " TEXT,"
				+ stuNumber + " INTEGER," + stuScore + " FLOAT)";
		db.execSQL(sql);

	}
   //�������ݿ�
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i(TAG, " onUpgrade() ");
		// ɾ�����SQL
		String sql = "DROP TABLE IF EXITS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
   //�ر����ݿ�
	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		Log.i(TAG, "close()");
		db.close();
		super.close();
	}
	
	// ��ѯ���е����� ������Cursor����(����id����������)
	public Cursor searchAllData()
	{
		Log.i(TAG, " searchAllData()");
		//asc������descΪ����Ĭ��Ϊasc��
		return db.query(TABLE_NAME, null, null, null, null, null,MySqliteHelper.ID+" ASC" );
	}
	//��������
	public void  insertData(String name,int number,float score )
	{
		ContentValues values=new ContentValues();
		values.put(MySqliteHelper.stuName, name);
		values.put(MySqliteHelper.stuNumber, number);
		values.put(MySqliteHelper.stuScore, score);
		long row=db.insert(TABLE_NAME, null, values);
		Log.i(TAG, "insertData row="+row);
	}
	//��ѯָ������Ϣ
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
	//�޸�����
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
	
	//ɾ������
	public void deleteStuInfo(int id)
	{
		int rowaffected =db.delete(TABLE_NAME, MySqliteHelper.ID+"="+id, null);
		Log.i(TAG, "deleteStuInfo()  rowaffected="+rowaffected);
	}
	//�ж��Ƿ���ڸ�ѧ������Ϣ
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
