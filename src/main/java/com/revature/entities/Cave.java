package com.revature.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * We are going to assume that a Cave can house many bears,
 * but a bear lives in only one cave
 */

@Entity
@Table(name = "caves")
public class Cave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "CUBIC_FEET_SIZE")
	private double cubicFeetSize;

	@Column(name = "HAS_WATER")

	private boolean hasWater;

	/*
	 * Multiplicity annotations:
	 * @OneToOne
	 * @OneToMany
	 * @ManyToOne
	 * @ManyToMany
	 * 
	 * @JoinColumn - define a column upon which to join tables
	 * @JoinTable - defines a junction table to map the relationship
	 */

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cave_id") // Define foreign key column
	List<Bear> occupants;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCubicFeetSize() {
		return cubicFeetSize;
	}

	public void setCubicFeetSize(double cubicFeetSize) {
		this.cubicFeetSize = cubicFeetSize;
	}

	public boolean isHasWater() {
		return hasWater;
	}

	public void setHasWater(boolean hasWater) {
		this.hasWater = hasWater;
	}

	public List<Bear> getOccupants() {
		return occupants;
	}

	public void setOccupants(List<Bear> occupants) {
		this.occupants = occupants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cubicFeetSize);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (hasWater ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((occupants == null) ? 0 : occupants.hashCode());
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
		Cave other = (Cave) obj;
		if (Double.doubleToLongBits(cubicFeetSize) != Double.doubleToLongBits(other.cubicFeetSize))
			return false;
		if (hasWater != other.hasWater)
			return false;
		if (id != other.id)
			return false;
		if (occupants == null) {
			if (other.occupants != null)
				return false;
		} else if (!occupants.equals(other.occupants))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cave [id=" + id + ", cubicFeetSize=" + cubicFeetSize + ", hasWater=" + hasWater + ", occupants="
				+ occupants + "]";
	}

	public Cave(int id, double cubicFeetSize, boolean hasWater, List<Bear> occupants) {
		super();
		this.id = id;
		this.cubicFeetSize = cubicFeetSize;
		this.hasWater = hasWater;
		this.occupants = occupants;
	}

	public Cave() {
		super();
	}

}
