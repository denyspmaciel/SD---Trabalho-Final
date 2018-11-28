package com.server;

import java.util.ArrayList;

import com.BD.BdTimes;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.protos.TimesProtos.Time;
import com.protos.TimesProtos.Times;


public class BdTimesEsqueleto {
	
	BdTimes times = new BdTimes();
	
	public ByteString AddTime(ByteString msg) {
		Time time = null;
		try {
			time = Time.parseFrom(msg);
		} 
		catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		
		String resposta = times.AddTime(time);
		
	
		ByteString res = ByteString.copyFromUtf8(resposta);
		
		return res;
	}
	
	
	public ByteString List(ByteString msg) throws InvalidProtocolBufferException{
		Time op = null;
		op = Time.parseFrom(msg);
		System.out.println(op.getNome());
		System.out.println();
		
		Times timesb = times.List();						
		return timesb.toByteString();
	}
	
	public ByteString RemoveTime(ByteString msg) {
		Time time = null;
		try {
			time = Time.parseFrom(msg);
		} 
		catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		
		String resposta = times.RemoveTime(time.getId());

		ByteString res = ByteString.copyFromUtf8(resposta);
		
		return res;
	}
	
	public ByteString BuscarTimeID(ByteString msg) {
		
		Time time = null;
		try {
			time = Time.parseFrom(msg);
		} 
		catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		Time resposta = times.BuscarTimeID(time.getId());
		
		return resposta.toByteString();
	}
	
	public ByteString BuscarTimeNome(ByteString msg) {
		
		Time time = null;
		try {
			time = Time.parseFrom(msg);
		} 
		catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		Time resposta = times.BuscarTimeNome(time.getNome());
		
		return resposta.toByteString();
	}
	
	
	public ByteString AddTitulo(ByteString msg) {
		Time time = null;
		try {
			time = Time.parseFrom(msg);
		} 
		catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		
		String resposta = times.AddTitulo(time);
		
	
		ByteString res = ByteString.copyFromUtf8(resposta);
		
		return res;
	}
}
