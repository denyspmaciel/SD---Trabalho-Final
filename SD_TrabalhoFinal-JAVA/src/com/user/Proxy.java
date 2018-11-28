package com.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import com.Message.MessageOuterClass.Message;
import com.client.Client;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.protos.TimesProtos.Time;
import com.protos.TimesProtos.Times;


public class Proxy {

	Client client = new Client();
	static int messageID;
	private ArrayList<Time> times;
	
	
	public String AddTime(Time t) {
        ByteString msg = t.toByteString();
        byte[] response = doOperation("BdTimes", "AddTime", msg);
        String resposta = new String(response);
        
        return resposta;
	}
	
	
	public Times List() {
		Time.Builder t = new Time.Builder();
		t.setData("list");
		t.setNome("list");
		t.setTitulos("list");
		ByteString msg = t.build().toByteString();
		
		byte[] response = doOperation("BdTimes", "List", msg);
		
		
		Times tm=null;
		try {
			tm = Times.parseFrom(response);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return tm;
	}
	
	public String RemoveTime(int id) {
		Time.Builder t = new Time.Builder();
		t.setId(id);
		t.setData("remove");
		t.setNome("remove");
		t.setTitulos("remove");
		ByteString msg = t.build().toByteString();
        
        byte[] response = doOperation("BdTimes", "RemoveTime", msg);
        String resposta = new String(response);
        
        return resposta;
		
	}
	
	public String AddTitulo(Time time) {
		ByteString msg = time.toByteString();
        byte[] response = doOperation("BdTimes", "AddTitulo", msg);
        String resposta = new String(response);
        
        return resposta;
	}
	
	/*public Time BuscarTimeID(int id) {
		Time.Builder t = new Time.Builder();
		t.setId(id);
		t.setData("busca");
		t.setNome("busca");
		t.setTitulos("busca");
		ByteString msg = t.build().toByteString();
        
        byte[] response = doOperation("BdTimes", "BuscarTimeID", msg);
        Time resposta = null;
        
        try {
			resposta = Time.parseFrom(response);
		} 
        catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
        
        return resposta;
	}*/
	
	public Time BuscarTimeNome(String nome) {
		Time.Builder t = new Time.Builder();
		t.setId(0);
		t.setData("busca");
		t.setNome(nome);
		t.setTitulos("busca");
		ByteString msg = t.build().toByteString();
        
        byte[] response = doOperation("BdTimes", "BuscarTimeNome", msg);
        Time resposta = null;
        
        try {
			resposta = Time.parseFrom(response);
		} 
        catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
        
        return resposta;
	}
	
	public byte[] doOperation(String objectRef, String methodID, ByteString argumentos) {
		byte[] message = empacotaMensagem(objectRef, methodID, argumentos);
		try {
			client.sendRequest(message);
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Message messagem = desempacotaMensagem(client.getResponse());
				
		return messagem.getArguments().toByteArray();
	}
	
	public byte[] empacotaMensagem(String objectRef, String methodID, ByteString argumentos) {
		ByteArrayOutputStream mensagem = new ByteArrayOutputStream(1024);
		try {
			Random r = new Random();
			messageID = r.nextInt();
			Message.newBuilder().setType(0).setId(messageID).setObjReference(objectRef).setMethodId(methodID).setArguments(argumentos).build().writeDelimitedTo(mensagem);		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return mensagem.toByteArray();
	}
	
	public Message desempacotaMensagem(byte[] resposta) {
		Message message = null;
		
		try {
			message = Message.parseDelimitedFrom(new ByteArrayInputStream(resposta));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return message;
	}
}
