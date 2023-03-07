package com.common;

import java.util.SortedSet;
import java.util.TreeSet;

public class Func {
	// 排序用
	public String reSort(String str) {
		if(!str.equals("")) {
			SortedSet<Integer> sortedSet = new TreeSet<Integer>();
			for(String s : str.split(",")) {
				if(!s.equals("")) { // 避免 ,1,2,3 第一個,分割是空白
					sortedSet.add(Integer.parseInt(s));
				}
			}
			str="";
			for(Integer s : sortedSet) str += "," + s ;
			sortedSet.clear();
		}
		return str;
	}
}
