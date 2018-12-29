package com.wk.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TestLambda {
	public static void main(String[] args) {
		
		List<String> list = Arrays.asList("q","w","r","t","y");
//		list.forEach(obj -> {System.out.println(obj);});
		Stream.iterate(0, i -> i+1 ).limit(list.size()).forEach(i -> {System.out.println(i+":"+list.get(i));});
		System.out.println("");
		List<String> li = null;
		Optional.ofNullable(list).ifPresent(obj -> obj.forEach(tmp -> {System.out.println("test1:"+tmp);}));
		
		Optional.ofNullable(li).orElse(new ArrayList<String>()).forEach(tmp -> {System.out.println("test2:"+tmp);});
	}
}
