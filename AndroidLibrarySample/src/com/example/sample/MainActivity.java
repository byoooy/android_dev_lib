package com.example.sample;

import com.android.library.json.JsonHelper;
import com.example.sample.bean.BaseBean;
import com.example.sample.bean.StudentBean;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.btn_json).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_json:
			jsonParse();
			break;
		}
	}

	private void jsonParse() {
		StudentBean student = new StudentBean();
		student.setName("小陈");
		student.setAge(20);
		student.setIntro("大学生");

		String studentString = JsonHelper.getInstance().parseString(student);
		Log.e("jsonParse", "StudentBean:" + studentString);
		BaseBean base = new BaseBean();
		base.setData(studentString);
		base.setCode(100);
		base.setInfo("json解析");

		String baseString = JsonHelper.getInstance().parseString(base);
		Log.e("jsonParse", "BaseBean:" + baseString);
		
		StudentBean s1 = JsonHelper.getInstance().parseObject(base.getData(), StudentBean.class);
		s1.toString();
	}

}
