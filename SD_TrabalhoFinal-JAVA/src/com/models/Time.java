package com.models;

public class Time {

	private int id;
	private String nome;
	private String data;
	private String titulos;
	
	public Time(int id, String nome, String data) {
		this.id = id;
		this.nome = nome;
		this.data = data;
	}
	
	public Time() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTitulos() {
		return this.titulos;
	}

	public void setTitulos(String titulos) {
		this.titulos = titulos;
	}
	
}
