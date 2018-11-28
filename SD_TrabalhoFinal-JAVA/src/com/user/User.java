package com.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

import com.protos.TimesProtos.Time;
import com.protos.TimesProtos.Times;

public class User {

	static Proxy Bd = new Proxy();
	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));


	public static void main(String[] args) throws IOException {
		String op = "";
		PrintStream stdout = System.out;

		while(!op.equals("6")) {

			stdout.print("*******Menu******\n");
			stdout.print("1 - Inserir Time\n");
			stdout.print("2 - Listar Times\n");
			stdout.print("3 - Remover Time\n");
			stdout.print("4 - Buscar Time\n");
			stdout.print("5 - Inserir Titulo\n");
			stdout.print("6 - Sair\n");
			//stdout.print("5 - Buscar Time por nome\n");
			stdout.print("Sua escolha: ");
			op = stdin.readLine();

			switch (op) {
			case "1":
				add();
				break;
			case "2":
				list();
				break;
			case "3":
				del();
				break;
			case "4":
				buscaNome();
				break;
			case "5":
				AddTitulo();
				break;
			case "6":
				stdout.print("\nAté a próxima companheiro! o/ o/\n");
				break;
			default:
				stdout.print("\nOpção inválida companheiro.\n");
				break;
			}
		}


	}

	public static void add() throws IOException {
		Time.Builder time = new Time.Builder();
		String nome, data, titulos;

		System.out.println("\nAdicionando time");
		System.out.print("Nome: ");
		nome = stdin.readLine();
		System.out.print("Data de criação: ");
		data = stdin.readLine();
		System.out.print("Títulos: ");
		titulos = stdin.readLine();

		time.setNome(nome);
		time.setData(data);
		time.setTitulos(titulos);

		//busca de o time exite
		Time buscaTime = Bd.BuscarTimeNome(nome);
		if (!buscaTime.getNome().equals("lixo")) {
			System.out.println("\n\nEsse time ja existe amigão\n\n");
		}
		else
		{

			if ( Bd.AddTime(time.build()).equals("true")){
				System.out.println("Time adicionado com sucesso.\n");
			}
			else {
				System.out.println("Erro ao adicionar o time.\n");
			}
		}

	}

	public static void list() {

		Times times = Bd.List();
		System.out.println("\nListando times");
		
		if (times.getTimeList().size() == 0) {
			System.out.println("\n\nNão ha times cadastrados no banco\n\n");
		}
		else {
			for (Time time : times.getTimeList()) {
				System.out.println("ID: "+time.getId());
				System.out.println("Nome: "+time.getNome());
				System.out.println("Data de criação: "+time.getData());
				System.out.println("Títulos: "+time.getTitulos());
				System.out.println("\n********************************\n");
			}
		}
	}

	public static void del() {

		Times times = Bd.List();
		System.out.println("\nLista de times cadastrados");

		for (Time time: times.getTimeList()) {
			System.out.println(time.getId()+"--"+time.getNome());
		}

		int id;
		Scanner sc = new Scanner(System.in);
		System.out.print("ID do time que será deletado: ");
		id = sc.nextInt();
		sc.nextLine();

		boolean menino_burro = true;
		for (Time time : times.getTimeList()) {
			if (id == time.getId()) {
				menino_burro = false;
			}
		}

		if (menino_burro) {
			System.out.println("\n\nQueridão o ID que você passou não existe\n\n");
		}
		else {
			if ( Bd.RemoveTime(id).equals("true")){
				System.out.println("Time removido.\n");
			}
			else {
				System.out.println("Erro ao remover o time.\n");
			}
		}
	}

	/*public static void buscaID() {
		System.out.println("\nBuscando time pelo ID");
		int id;
		Scanner sc = new Scanner(System.in);
		System.out.print("ID: ");
		id = sc.nextInt();
		sc.nextLine();

		Time time = Bd.BuscarTimeID(id);
		System.out.println("Nome: "+time.getNome());
		System.out.println("Data de criação: "+time.getData());
		System.out.println("Títulos: "+time.getTitulos()+"\n");

	}*/

	public static void buscaNome() {
		System.out.println("\nBuscando time pelo nome");
		String nome;
		Scanner sc = new Scanner(System.in);
		System.out.print("Nome: ");
		nome = sc.nextLine();
		//sc.nextLine();

		Time time = Bd.BuscarTimeNome(nome);
		if(!time.getNome().equals("lixo")) {
			System.out.println("ID: "+time.getId());
			System.out.println("Data de criação: "+time.getData());
			System.out.println("Títulos: "+time.getTitulos()+"\n");
		}
		else {
			System.out.println("Time não encontrado");
		}

	}

	public static void AddTitulo() {
		Times times = Bd.List();
		System.out.println("\nLista de times cadastrados");

		for (Time time: times.getTimeList()) {
			System.out.println(time.getId()+"--"+time.getNome());
		}

		int id;
		Scanner sc = new Scanner(System.in);
		System.out.print("ID do time que será adicionado o titulo: ");
		id = sc.nextInt();
		sc.nextLine();

		boolean menino_burro = true;
		for (Time time : times.getTimeList()) {
			if (id == time.getId()) {
				menino_burro = false;
			}
		}

		if(menino_burro) {
			System.out.println("\n\nQueridão o ID que você passou não existe\n\n");
		}
		else
		{
			Time alterar = null;
			//pegar o time escolhido
			for (Time time :times.getTimeList()) {
				if(time.getId() == id) {
					alterar = time;
					break;
				}
			}
			//criar um novo time com o time escolhido
			Time.Builder alterado = Time.newBuilder();
			alterado.setData(alterar.getData());
			alterado.setNome(alterar.getNome());
			alterado.setId(alterar.getId());

			System.out.println("Titulos atuais do time do Time:" + alterar.getNome());
			System.out.println("+--------------------------------------------------+");
			System.out.println(alterar.getTitulos());
			System.out.println("+--------------------------------------------------+");
			System.out.println("Informe o novo Título");
			String novotitulo = sc.nextLine();

			alterado.setTitulos(alterar.getTitulos()+" : "+ novotitulo);


			if ( Bd.AddTitulo(alterado.build()).equals("true")){
				System.out.println("Título adicionado com sucesso.\n");
			}
			else {
				System.out.println("Erro ao adicionar título.\n");
			}

		}
	}
}

