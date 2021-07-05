package com.inspo.solutions.model;

public class Student {
	private long id;
	private String firstName;
	private String lastName;
	private double cgpa;
	
	public Student(long id, String firstName, String lastName, double cgpa) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cgpa = cgpa;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getCgpa() {
		return cgpa;
	}
	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}
	
	public static class StudentBuilder{
		
		private long id;
		private String firstName;
		private String lastName;
		private double cgpa;
		
		
		public StudentBuilder setId(long id) {
			this.id = id;
			return this;
		}
		public StudentBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public StudentBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public StudentBuilder setCgpa(double cgpa) {
			this.cgpa = cgpa;
			return this;
		}
		
		public Student build() {
			return new Student(id,firstName,lastName,cgpa);
		}
		
	}
	
}
