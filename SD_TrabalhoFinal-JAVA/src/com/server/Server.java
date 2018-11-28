package com.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

import com.Message.MessageOuterClass.Message;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class Server {
	public static void main(String[] args) {
		DatagramSocket aSocket = null;
		Despachante despachante = new Despachante();
		try {
			aSocket = new DatagramSocket(6789);
			byte[] buffer = new byte[1000];
			int newId;
			int oldId = 0;
			byte[] pacoteRep = null;
			
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);
				
				newId = desempacotaRequisicao(request.getData()).getId();
				
				if (newId == oldId) {
					System.out.println("ouve repetição");
					DatagramPacket res = new DatagramPacket(pacoteRep, pacoteRep.length, request.getAddress(), request.getPort());
					aSocket.send(res);
				}else{
					ByteString response = despachante.selecionaEqueleto(desempacotaRequisicao(request.getData()));
					byte[] pacote = empacotaResposta(response.toByteArray(),
							desempacotaRequisicao(request.getData()).getId());
					//guarda para caso de duplicação
					pacoteRep = pacote;
					oldId = desempacotaRequisicao(request.getData()).getId();
					
					DatagramPacket res = new DatagramPacket(pacote, pacote.length, request.getAddress(), request.getPort());
					aSocket.send(res);
					
				}
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}

	}

	public static Message desempacotaRequisicao(byte[] requisicao) throws IOException {
		Message mensagem = null;

		ByteArrayInputStream entrada = new ByteArrayInputStream(requisicao);

		try {
			mensagem = Message.parseDelimitedFrom(entrada);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return mensagem;
	}

	public static byte[] empacotaResposta(byte[] msg, int requestID) {
		ByteArrayOutputStream mensagem = new ByteArrayOutputStream(1024);
		try {
			Message.newBuilder().setType(1).setArguments(ByteString.copyFrom(msg)).setId(requestID).build()
					.writeDelimitedTo(mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mensagem.toByteArray();
	}

}
