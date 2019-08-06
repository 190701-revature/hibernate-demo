package com.revature.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* Using JPA Annotations to configure an Entity */
/*
 * @Entity - Tells Hibernate (or any JPA implementing ORM) that this class
 * represents an Entity that can be persisted.
 * 
 * @Table (optional) - Provides optional table configuration
 * 
 * @Id - Indicates the primary key of a table
 */


@Entity
@Table(name="bears")
public class Bear {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String breed;
	private double kilograms;
	private String favoriteFood;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public double getKilograms() {
		return kilograms;
	}

	public void setKilograms(double kilograms) {
		this.kilograms = kilograms;
	}

	public String getFavoriteFood() {
		return favoriteFood;
	}

	public void setFavoriteFood(String favoriteFood) {
		this.favoriteFood = favoriteFood;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breed == null) ? 0 : breed.hashCode());
		result = prime * result + ((favoriteFood == null) ? 0 : favoriteFood.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(kilograms);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bear other = (Bear) obj;
		if (breed == null) {
			if (other.breed != null)
				return false;
		} else if (!breed.equals(other.breed))
			return false;
		if (favoriteFood == null) {
			if (other.favoriteFood != null)
				return false;
		} else if (!favoriteFood.equals(other.favoriteFood))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(kilograms) != Double.doubleToLongBits(other.kilograms))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bear [id=" + id + ", breed=" + breed + ", kilograms=" + kilograms + ", favoriteFood=" + favoriteFood
				+ "]";
	}

	public Bear() {
		super();
	}

	public Bear(int id, String breed, double kilograms, String favoriteFood) {
		super();
		this.id = id;
		this.breed = breed;
		this.kilograms = kilograms;
		this.favoriteFood = favoriteFood;
	}

}
