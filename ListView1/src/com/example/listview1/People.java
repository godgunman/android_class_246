package com.example.listview1;

public class People {

	private String name;
	private int age;

	public People(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return name + ":" + age;
	}

}
