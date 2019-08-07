package com.revature.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

/* Using JPA Annotations to configure an Entity */
/*
 * @Entity - Tells Hibernate (or any JPA implementing ORM) that this class
 * represents an Entity that can be persisted.
 * 
 * @Table (optional) - Provides optional table configuration
 * 
 * @Id - Indicates the primary key of a table
 * 
 * @GeneratedValue - Annotation used to configure how Hibernate generates values
 * 
 * @Column - Optional Column configuration such as Not Null or column name
 */

@Entity
@Table(name = "bears")
@Check(constraints = "kilograms > 0")
@NamedQuery(name = "favoriteFoodQuery", 
	query = "from Bear b WHERE favoriteFood like :favoriteFood")

public class Bear {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// nullable = false -> Not Null
	// If we don't use an @Column annotation, will this be persisted?
	// -Yes it will be. To prevent tracking, we can use @Transient
	@Column(nullable = false)
	private String breed;

	private double kilograms;

	@Column(name = "favorite_food")
	private String favoriteFood;

	@ManyToOne
	@JoinColumn(name = "cave_id")
	private Cave cave;
	
	
	@OneToOne
	@JoinColumn(name = "honey_jar_id")
	private HoneyJar honeyJar;
	
	@ManyToMany
	@JoinTable(name="bear_cubs", joinColumns = { @JoinColumn(name="parent_id") },
			inverseJoinColumns = { @JoinColumn(name="cub_id")})
	List<Bear> cubs;
	
	@ManyToMany
	@JoinTable(name="bear_cubs", joinColumns = { @JoinColumn(name="cub_id")},
			inverseJoinColumns = { @JoinColumn(name="parent_id")})
	List<Bear> parents;

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

	public Cave getCave() {
		return cave;
	}

	public void setCave(Cave cave) {
		this.cave = cave;
	}

	public HoneyJar getHoneyjar() {
		return honeyJar;
	}

	public void setHoneyjar(HoneyJar honeyjar) {
		this.honeyJar = honeyjar;
	}

	
	
	public HoneyJar getHoneyJar() {
		return honeyJar;
	}

	public void setHoneyJar(HoneyJar honeyJar) {
		this.honeyJar = honeyJar;
	}

	public List<Bear> getCubs() {
		return cubs;
	}

	public void setCubs(List<Bear> cubs) {
		this.cubs = cubs;
	}

	public List<Bear> getParents() {
		return parents;
	}

	public void setParents(List<Bear> parents) {
		this.parents = parents;
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
