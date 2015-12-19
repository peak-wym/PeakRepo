package com.example.sqlitedemo;

import android.os.Bundle;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

/*
 *  Demo �������ݿ�SQlite��ʹ��
 *  author: ������
 *  QQ:893115871
 *  Email:gfj19900401@163.com
 * 
 * */
public class SQliteDemo extends TabActivity {
	//���ֱ���������
	private static final String QUERY_TAG = "��ѯTAG";
	private static final String INSERT_TAG = "���TAG";
	private static final String UPDATE_TAG = "�޸�TAG";
	private static final String DELETE_TAG = "ɾ��TAG";
	private TabHost mTabHost;
	private View mViews;
	private EditText  mEditText_Name,mEditText_Number,mEditText_Score;
	private EditText mEditText_query;
	private Button mButton_query,mButton_insert;
	private ListView query_ListView,insert_ListView;
	private SimpleCursorAdapter adapter;//ListView��������
	private MySqliteHelper    database;
	private  Cursor mCursor;//�α꣬��ѯ�󷵻صĶ���
	private int _id;//��ǰ�α�Cursor���ڵ��ֶ�ֵ
	
//	private boolean isInsert=false; 
	private Cursor mCursor_query;
	private String  name,number,score;
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���ñ�����ɫ������
		this.setTitle(getResources().getString(R.string.title));
		this.setTitleColor(Color.MAGENTA);
		
		//���TabHost����
		mTabHost = this.getTabHost();
		//ͨ������ѡ����������е����
		mViews = LayoutInflater.from(this).inflate(
				R.layout.activity_sqlite_demo, mTabHost.getTabContentView(),
				true);
		this.findViewsAndSetListener();//������Widget
		this.addTabs();//���TAB
		
		//����SQLiteOpenHelper���������
		database=new MySqliteHelper(this);
				
		showUiAdapter();//���½��棨������������
		
	}

	public void addTabs() {
		mTabHost.addTab(mTabHost.newTabSpec(QUERY_TAG).setContent(R.id.queryLayout_id)
				.setIndicator(getResources().getString(R.string.query_str),getResources().getDrawable(R.drawable.query)));
		mTabHost.addTab(mTabHost.newTabSpec(INSERT_TAG).setContent(R.id.insertLayout_id)
				.setIndicator(getResources().getString(R.string.insert_str),getResources().getDrawable(R.drawable.add)));
		mTabHost.addTab(mTabHost.newTabSpec(UPDATE_TAG).setContent(R.id.insertLayout_id)
				.setIndicator(getResources().getString(R.string.update_str),getResources().getDrawable(R.drawable.refresh)));
		mTabHost.addTab(mTabHost.newTabSpec(DELETE_TAG).setContent(R.id.insertLayout_id)
				.setIndicator(getResources().getString(R.string.delete_str),getResources().getDrawable(R.drawable.delete)));
	
		mTabHost.setCurrentTab(1);//���õ�ǰ��ʾ��2��tab  
		//���л��ǵ��¼�����
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if (tabId.equalsIgnoreCase(SQliteDemo.INSERT_TAG)) {
					
					mEditText_Name.setEnabled(true);
					mEditText_Number.setEnabled(true);
					mEditText_Score.setEnabled(true);
					mButton_insert.setText(getResources().getString(R.string.insert_str));
					
					Until.setSearch(false);
					Until.setInsert(true);
					Until.setDelete(false);
					setHintText();
					insert_ListView.setOnItemClickListener(myOnItemClickListener);
				} else if (tabId.equalsIgnoreCase(SQliteDemo.UPDATE_TAG)) {
					
					mEditText_Name.setEnabled(false);
					mEditText_Number.setEnabled(false);
					mEditText_Score.setEnabled(false);
					mButton_insert.setText(getResources().getString(R.string.update_str));
					Until.setSearch(false);
					Until.setInsert(false);
					Until.setDelete(false);
					setHintEmptyText();
					insert_ListView.setOnItemClickListener(myOnItemClickListener);
				} else if (tabId.equalsIgnoreCase(SQliteDemo.DELETE_TAG)) {
					
					mEditText_Name.setEnabled(false);
					mEditText_Number.setEnabled(false);
					mEditText_Score.setEnabled(false);
					mButton_insert.setText(getResources().getString(R.string.delete_str));
					Until.setSearch(false);
					Until.setInsert(false);
					Until.setDelete(true);
					setHintEmptyText();
					insert_ListView.setOnItemClickListener(myOnItemClickListener);
				}else{
					Until.setSearch(true);
					Until.setInsert(false);
					Until.setDelete(false);
					mEditText_query.setHint(getResources().getString(R.string.query_edit_hint));
				}
				
			  Toast.makeText(getApplicationContext(), "������>>" + tabId + "<<ѡ��",
     					Toast.LENGTH_SHORT).show();
			}
		});
	}
	//�ҵ����
	public void findViewsAndSetListener()
	{
		EditText editTexts[]={
				mEditText_Name=(EditText)this.findViewById(R.id.insert_editName_id),
				mEditText_Number=(EditText)this.findViewById(R.id.insert_editNumber_id),
				mEditText_Score=(EditText)this.findViewById(R.id.insert_editScore_id)
		};
		mEditText_query=(EditText)this.findViewById(R.id.query_edit_id);
		
		mButton_query=(Button)this.findViewById(R.id.query_button_id);
		mButton_insert=(Button)this.findViewById(R.id.insert_button_id);
		
		query_ListView=(ListView)this.findViewById(R.id.query_list_id);
		insert_ListView=(ListView)this.findViewById(R.id.insert_list_id);
		
		mButton_query.setOnClickListener(queryButton_OnClickListener);
		mButton_insert.setOnClickListener(insertButton_OnClickListener);
		
		for (int i = 0; i < editTexts.length; i++) {
			editTexts[i].setOnClickListener(listener);
		}
		mEditText_query.setOnClickListener(listener);
		mEditText_query.setOnKeyListener(keyListener);
		
	}
	//���������¼�
	OnKeyListener keyListener=new OnKeyListener(){

		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (!mEditText_query.getText().toString().equalsIgnoreCase("")) {
				mButton_query.setEnabled(true);
			}else{
				mButton_query.setEnabled(false);
			}
			
			return false;
		}
		
	};
	//���������������½��� 
	@SuppressWarnings("deprecation")
	public void  showUiAdapter()
	{
		mCursor=database.searchAllData();
		// if�ǲ�ѯ�򽫲�ѯ����α긳��mCursor
		if (Until.isSearch()) {
			mCursor=mCursor_query;
		}
    if (mCursor != null && mCursor.getCount() >= 0)
	 {
		adapter=new SimpleCursorAdapter(this,
				R.layout.list_item, 
				mCursor,
				new String[]{
				MySqliteHelper.ID,
				MySqliteHelper.stuName,
				MySqliteHelper.stuNumber,
				MySqliteHelper.stuScore
		        }, 
				new int []{
				R.id.id_id,
				R.id.name_id,
				R.id.number_id,
				R.id.score_id
		});
		//ΪListView����������
				if (Until.isSearch()) {
					query_ListView.setAdapter(adapter);
				}else{
					insert_ListView.setAdapter(adapter);
				}
	 }else{
		 Toast.makeText(SQliteDemo.this, "û����Ϣ��", Toast.LENGTH_SHORT).show();
	 }
			
	}
	//��ʾ��Ϊ��
	public void setHintEmptyText()
	{
		mEditText_Name.setHint("");
		mEditText_Number.setHint("");
		mEditText_Score.setHint("");
		
	}
	//������ʾ��
	public void setHintText()
	{
		mEditText_Name.setHint(getResources().getString(R.string.insert_editName_hint));
		mEditText_Number.setHint(getResources().getString(R.string.insert_editNumber_hint));
		mEditText_Score.setHint(getResources().getString(R.string.insert_editScore_hint));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sqlite_demo, menu);
		return true;
	}

	OnClickListener queryButton_OnClickListener=new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			searchSpecificStuMethod();
		}
		
	};
	OnClickListener insertButton_OnClickListener=new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mButton_insert.getText().equals(getResources().getString(R.string.insert_str))) {
				insertMethod();
			} else if (mButton_insert.getText().equals(getResources().getString(R.string.update_str))){
				upDateMethod();
			}else if (mButton_insert.getText().equals(getResources().getString(R.string.delete_str))){
				deleteDataMethod();
			}
		}
		
	};
	OnClickListener listener=new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.insert_editName_id:
				mEditText_Name.setHint("");
				break;
			case R.id.insert_editNumber_id:
				mEditText_Number.setHint("");
				break;

			case R.id.insert_editScore_id:
				mEditText_Score.setHint("");
				break;
			case R.id.query_edit_id:
				mEditText_query.setHint("");
				break;

			default:
				break;
			}
	
		}
		
	};
	OnItemClickListener myOnItemClickListener=new OnItemClickListener(){

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//�����ı��༭�����Ա༭
			if (Until.isDelete()) {
				mEditText_Name.setEnabled(false);
				mEditText_Number.setEnabled(false);
				mEditText_Score.setEnabled(false);
			}else{
				mEditText_Name.setEnabled(true);
				mEditText_Number.setEnabled(true);
				mEditText_Score.setEnabled(true);
			}
			
			
			//�ƶ��α�
			mCursor.moveToPosition(arg2);
			_id=mCursor.getInt(mCursor.getColumnIndex(MySqliteHelper.ID));
			mEditText_Name.setText(mCursor.getString(mCursor.getColumnIndex(MySqliteHelper.stuName)));
			mEditText_Number.setText(mCursor.getString(mCursor.getColumnIndex(MySqliteHelper.stuNumber)));
			mEditText_Score.setText(mCursor.getString(mCursor.getColumnIndex(MySqliteHelper.stuScore)));
		}
		
	};
	//��������
	public void insertMethod()
	{
		Until.setInsert(true);
		if(isValid())
		{
			Cursor c=database.isHaveThisStu(Integer.valueOf(number));
			if (c.getCount()>0&& c!=null) {
				 Toast.makeText(this, getResources().getString(R.string.haveNumber), 1000).show();
			} else {
				Log.i("//1////", "11111111111");
				database.insertData(name, Integer.valueOf(number),  Float.valueOf(score));
				update();//��֪����������	 
			}
			
		}
	//	update();//��֪����������	 
	}
	//��֪����������
	public void  update()
	{
		_id=0;
		mEditText_Name.setText("");
		mEditText_Number.setText("");
		mEditText_Score.setText("");
		
		//�ж��Ƿ�Ϊ�޸Ļ�ɾ�����������Ƿ�������ʾ��
		if (Until.isUpdataOrDelete()) {
			 setHintEmptyText();
		} else {
			 setHintText();
		}
		
		mCursor.requery();
		adapter.notifyDataSetChanged();
		//�����ı��༭�������Ա༭
		Log.i("//////", ""+Until.isInsert());
		if (Until.isInsert()) {
			mEditText_Name.setEnabled(true);
			mEditText_Number.setEnabled(true);
			mEditText_Score.setEnabled(true);
		} else {
			mEditText_Name.setEnabled(false);
			mEditText_Number.setEnabled(false);
			mEditText_Score.setEnabled(false);
		}
		
	}
	//��ѯָ��ѧ�ŵ�ѧ���ɼ���Ϣ
	public void  searchSpecificStuMethod()
	{
		Until.setSearch(true);
		String number=mEditText_query.getText().toString().trim();
		if (number.equalsIgnoreCase("")) {
			return;
		}
	    mCursor_query=database.searchSpecific(Integer.valueOf(number));
	    if (mCursor_query.getCount()>0) {
		   showUiAdapter();
		} else {
			Toast.makeText(this, getResources().getString(R.string.noinfo), Toast.LENGTH_SHORT).show(); 
		}	
		mEditText_query.setHint(getResources().getString(R.string.query_edit_hint));
		
	}
	//�հ����Ƿ������кϷ�
	public boolean  isValid()
	{
		 name=null;number=null;score=null;
		 name=mEditText_Name.getText().toString().trim();
		 number=mEditText_Number.getText().toString().trim();
		 score=mEditText_Score.getText().toString().trim();
		if (name.equalsIgnoreCase("")||number.equalsIgnoreCase("")||score.equalsIgnoreCase("")) {
			Toast.makeText(SQliteDemo.this, "��������,��˶ԣ�", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			return true;
		}
	}
	//�޸�����
	public void  upDateMethod()
	{
		Until.setUpdataOrDelete(true);
		if(isValid())
		{
			database.upDateinfo(_id, name, Integer.valueOf(number), Float.valueOf(score));
		}
	    update();//��֪����������
		
	}
	//ɾ�����ݵ�function
	public void  deleteDataMethod()
	{
		Until.setUpdataOrDelete(true);
		if(isValid())
		{
			database.deleteStuInfo(_id);
		}
		 update();//��֪����������
		 //ɾ��ʼ��δ���ɱ༭Ŷ
		mEditText_Name.setEnabled(false);
		mEditText_Number.setEnabled(false);
		mEditText_Score.setEnabled(false);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {//�˳���
			this.exitDailog();
		}
		
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_about:
			this.showMyDialog();
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	public void exitDailog()
	{
		new AlertDialog.Builder(this)
		.setIcon(R.drawable.comment)
		.setTitle(getResources().getString(R.string.dialog_title))
		.setMessage(getResources().getString(R.string.app_exit))
		.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				database.close();
				finish();
			}
		})
		.setNegativeButton(getResources().getString(R.string.cancle), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.show();
	}
	public void showMyDialog()
	{
		new AlertDialog.Builder(this)
		.setIcon(R.drawable.comment)
		.setTitle(getResources().getString(R.string.dialog_title))
		.setMessage(getResources().getString(R.string.dialog_msg))
		.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.show();
	}
}
