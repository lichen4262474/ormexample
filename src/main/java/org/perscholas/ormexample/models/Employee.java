package org.perscholas.ormexample.models;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "emp")
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
	
	@ManyToMany
	List<Department> dep;

	
}

