package com.example.sample.adapterhelper;

import com.android.library.adapter.BaseAdapterHelper;
import com.example.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class AdapterHelperActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);

		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(new Adapter());
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 20;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			return BaseAdapterHelper
					.get(AdapterHelperActivity.this, convertView, parent,
							R.layout.item_list).setText(R.id.text, "测试")
					.setImageResource(R.id.image, R.drawable.ic_launcher)
					.getView();
		}

	}
}
