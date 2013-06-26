package com.example.dbdemo;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DemoAdapter extends BaseAdapter {
	
	private List<Map<String, Object>> items;//数据接收
	private Context context;//上下文
	private ViewHolder holder;
	private LayoutInflater inflater;
	
	public DemoAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	

	public DemoAdapter(Context context, List<Map<String, Object>> items) {
		this.context = context;
		this.items = items;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}



	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = inflater.inflate(R.layout.listview, null);
			holder = new ViewHolder();
			holder.tvId = (TextView) view.findViewById(R.id.tvID);
			holder.tvName = (TextView) view.findViewById(R.id.tvName);
			holder.tvAge = (TextView) view.findViewById(R.id.tvAge);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.tvName.setText(items.get(position).get("name").toString());
		holder.tvAge.setText(items.get(position).get("age").toString());
		holder.tvId.setText(items.get(position).get("id").toString());
		return view;
	}
	
	private static class ViewHolder{
		public TextView tvId;
		public TextView tvName;
		public TextView tvAge;
		
	}

}
