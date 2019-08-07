package com.revature.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
 * Bears have a honey jar.
 * And they do not share their honey jar.
 * This will demonstrate a @OneToOne relationship and mapping.
 */

@Entity
@Table(name="HONEY_JARS")
public class HoneyJar {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="VOLUME_LITERS")
	private double volumeLiters;
	
	@Column(name="HONEY_LITERS")
	private double honeyLiters;

	@OneToOne(mappedBy = "honeyJar")
	private Bear owner;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getVolumeLiters() {
		return volumeLiters;
	}

	public void setVolumeLiters(double volumeLiters) {
		this.volumeLiters = volumeLiters;
	}

	public double getHoneyLiters() {
		return honeyLiters;
	}

	public void setHoneyLiters(double honeyLiters) {
		this.honeyLiters = honeyLiters;
	}

	public Bear getOwner() {
		return owner;
	}

	public void setOwner(Bear owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(honeyLiters);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		temp = Double.doubleToLongBits(volumeLiters);
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
		HoneyJar other = (HoneyJar) obj;
		if (Double.doubleToLongBits(honeyLiters) != Double.doubleToLongBits(other.honeyLiters))
			return false;
		if (id != other.id)
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (Double.doubleToLongBits(volumeLiters) != Double.doubleToLongBits(other.volumeLiters))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HoneyJar [id=" + id + ", volumeLiters=" + volumeLiters + ", honeyLiters=" + honeyLiters + ", owner="
				+ owner + "]";
	}

	public HoneyJar(int id, double volumeLiters, double honeyLiters, Bear owner) {
		super();
		this.id = id;
		this.volumeLiters = volumeLiters;
		this.honeyLiters = honeyLiters;
		this.owner = owner;
	}

	public HoneyJar() {
		super();
	}

}
