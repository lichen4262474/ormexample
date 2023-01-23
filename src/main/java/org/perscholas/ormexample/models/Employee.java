package org.perscholas.ormexample.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@NamedQueries({
	@NamedQuery(name = "getAll", query = "from Employee"),
	@NamedQuery(name = "getById", query = "from Employee where id = :id")
})


public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@NonNull
	@Column(columnDefinition = "varchar(50)")
	String name;
	@NonNull
	Date dob;
	@NonNull
	String password;
	
	@ToString.Exclude
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.DETACH},
			fetch = FetchType.EAGER)
	@JoinTable(name = "emp_dep_jointable",
	joinColumns = @JoinColumn(name="employee_id"),
	inverseJoinColumns = @JoinColumn(name="department_id"))
	List<Department> dep = new ArrayList<>();
	
	
	public void addDepartment(Department d) {
		dep.add(d);
		d.getEmp().add(this);
			
	}

	
}

