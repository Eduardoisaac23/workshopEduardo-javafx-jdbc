package model.dao;

import java.util.List;

import model.entities.Departmento;

public interface DepartmentDao {

	void insert(Departmento obj);
	void update(Departmento obj);
	void deleteById(Integer id);
	Departmento findById(Integer id);
	List<Departmento> findAll();
}
