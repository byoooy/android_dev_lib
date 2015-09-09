package com.example.sample.bean;

import android.util.Log;

public class StudentBean extends BaseBean {
	private String name;

	private int age;

	private String intro;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Override
	public String toString() {
		Log.e("StudentBean", "name:" + name + ";age:" + age + ";intro:" + intro);
		return super.toString();
	}

}
