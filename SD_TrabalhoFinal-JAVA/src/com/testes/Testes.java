package com.testes;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.client.Client;
import com.protos.TimesProtos.Time;

public class Testes {

	
	public static void main(String[] args) {
	Time.Builder time = Time.newBuilder();
	time.setData("000");
	time.setNome("000");
	time.setTitulos("000");
	
	Time t = time.build();	
	
	Time.Builder time2 = Time.newBuilder();
	time2.setData("000");
	time2.setNome("000");
	time2.setTitulos("000");
	
	Time t2 = time2.build();	
	
	byte[] a1 = t.toByteArray();
	byte[] a2 = t2.toByteArray();
	
	if (Arrays.equals(a1, a2)) {
		System.out.println("iguais");
		System.out.println(a2);
		System.out.println(a1);
	}else {
		System.out.println("Não iguais");
	}
	
	}
	
}
