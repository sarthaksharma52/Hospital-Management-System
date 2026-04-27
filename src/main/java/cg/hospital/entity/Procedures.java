package cg.hospital.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Procedures")
public class Procedures {

    @Id
    @Column(name = "Code")
    private Integer code;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;

    @Column(name = "Cost", nullable = false)
    private Double cost;

    // 🔹 Default Constructor (Required by JPA)
    public Procedures() {
    }

    // 🔹 Parameterized Constructor
    public Procedures(Integer code, String name, Double cost) {
        this.code = code;
        this.name = name;
        this.cost = cost;
    }

    // 🔹 Getter for code
    public Integer getCode() {
        return code;
    }

    // 🔹 Setter for code
    public void setCode(Integer code) {
        this.code = code;
    }

    // 🔹 Getter for name
    public String getName() {
        return name;
    }

    // 🔹 Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // 🔹 Getter for cost
    public Double getCost() {
        return cost;
    }

    // 🔹 Setter for cost
    public void setCost(Double cost) {
        this.cost = cost;
    }

    // 🔹 toString() method
    @Override
    public String toString() {
        return "Procedures{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

	/*
	 * @Override public boolean equals(Object o) { if (this == o) return true; if (o
	 * == null || getClass() != o.getClass()) return false;
	 * 
	 * Procedures that = (Procedures) o;
	 * 
	 * return code != null ? code.equals(that.code) : that.code == null; }
	 * 
	 * @Override public int hashCode() { return code != null ? code.hashCode() : 0;
	 * }
	 */
}