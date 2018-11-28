package com.DAO;

//See README.txt for information and build instructions.

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.protos.TimesProtos.Time;
import com.protos.TimesProtos.Times;

class ListTime {
	// Iterates though all people in the AddressBook and prints info about them.
	static void Print(Times times) {
		for (Time time: times.getTimeList()) {
			System.out.println("ID: " + time.getId());
			System.out.println("Nome: " + time.getNome());
			System.out.println("Data de criação: " + time.getData());
			System.out.println("Título: " + time.getTitulos());
		}
	}

	// Main function:  Reads the entire address book from a file and prints all
	//   the information inside.
	public static void main(String[] args) throws Exception {

		// Read the existing address book.
		Times time = Times.parseFrom(new FileInputStream("arquivo"));

		Print(time);
	}
}