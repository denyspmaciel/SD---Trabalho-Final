package com.DAO;

//See README.txt for information and build instructions.

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;

import com.protos.TimesProtos.Time;
import com.protos.TimesProtos.Times;

class AddTime {
	static Time PromptForAddress(BufferedReader stdin, PrintStream stdout) throws IOException {
		Time.Builder time = Time.newBuilder();

		stdout.print("Digite o ID: ");
		time.setId(Integer.valueOf(stdin.readLine()));

		stdout.print("Digite o nome: ");
		time.setNome(stdin.readLine());

		stdout.print("Digite a data de criação: ");
		time.setData(stdin.readLine());

		stdout.print("Digite o título: ");
		time.setTitulos(stdin.readLine());

		return time.build();
	}

	// Main function:  Reads the entire address book from a file,
	//   adds one person based on user input, then writes it back out to the same
	//   file.
	public static void main(String[] args) throws Exception {

		Times.Builder times = Times.newBuilder();

		// Read the existing address book.
		try {
			FileInputStream input = new FileInputStream("arquivo");
			try {
				times.mergeFrom(input);
			} finally {
				try { input.close(); } catch (Throwable ignore) {}
			}
		} catch (FileNotFoundException e) {
			System.out.println("arquivo" + ": File not found.  Creating a new file.");
		}

		// Add an address.
		times.addTime(
				PromptForAddress(new BufferedReader(new InputStreamReader(System.in)),System.out));
		// Write the new address book back to disk.
		FileOutputStream output = new FileOutputStream("arquivo");
		try {
			times.build().writeTo(output);
		} finally {
			output.close();
		}
	}
}