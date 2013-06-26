package com.example.dbdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ListActivity {

	private static String DB_NAME = "mydb";
	private EditText et_name;
	private EditText et_age;
	private DbHelper dbhelper;
	private SQLiteDatabase db;
	private Cursor cursor;
	private DemoAdapter listAdapter;
	private Button selBt,addBt,upBt,delBt;
	private String selId;
	private List<Map<String, Object>>items = new ArrayList<Map<String,Object>>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		et_name = (EditText) findViewById(R.id.et_name);
		et_age = (EditText) findViewById(R.id.et_age);
//		listView = (ListView) findViewById(R.id.listView);
		selBt = (Button) findViewById(R.id.bt_query);
		addBt = (Button) findViewById(R.id.bt_add);
		delBt = (Button) findViewById(R.id.bt_delete);
		upBt = (Button) findViewById(R.id.bt_modify);
		//getData();
		listAdapter = new DemoAdapter(MainActivity.this,items);
		setListAdapter(listAdapter);
		
		selBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbFindAll();
			}
		});
		
		addBt.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dbAdd();
						dbFindAll();
					}
				});
		
		upBt.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dbUpdate();
						dbFindAll();
					}
				});
		
		delBt.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dbDel();
						dbFindAll();
					}
				});
		
		dbhelper = new DbHelper(this, DB_NAME, null, 1);
		db = dbhelper.getWritableDatabase();//打开数据库
		dbFindAll();
		


	}


	protected void dbDel() {
		selId = et_age.getText().toString().trim();
		String where = "id = " + selId;
		int i = db.delete(dbhelper.TB_NAME, where, null);
		if (i > 0) {
			Log.i("mydemo", "成功");
			et_age.setText("");
		} else {
			Log.i("mydemo", "失败");
		}
		
	}

	protected void dbUpdate() {
		ContentValues values = new ContentValues();
		values.put("name", et_name.getText().toString().trim());
		values.put("age", et_age.getText().toString().trim());
		String where = "id = " + et_age.getText().toString().trim();
		int i = db.update(dbhelper.TB_NAME,values, where, null);
		if (i > 0) {
			Log.i("mydemo", "成功");
		} else {
			Log.i("mydemo", "失败");
		}
	}

	protected void dbAdd() {
		ContentValues values = new ContentValues();
		values.put("name", et_name.getText().toString().trim());
		values.put("age", et_age.getText().toString().trim());
		long rowid = db.insert(dbhelper.TB_NAME, null, values);
		if (rowid == -1) {
			Log.i("mydemo", "失败");
		} else {
			Log.i("mydemo", "成功" + rowid);
		}
	}

	protected void dbFindAll() {
		items.clear();
		cursor = db.query(dbhelper.TB_NAME, null, null, null, null, null, "id ASC");
		cursor.moveToFirst();
		Map<String, Object> map = null;
		while(!cursor.isAfterLast()){
			map = new HashMap<String, Object>();
			map.put("id", cursor.getString(0));
			map.put("name", cursor.getString(1));
			map.put("age", cursor.getString(2));
			items.add(map);
			cursor.moveToNext();
		}
		
		listAdapter.notifyDataSetChanged();
		
	}


}
