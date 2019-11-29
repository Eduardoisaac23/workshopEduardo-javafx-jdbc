package model.Services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Departmento;



public class DepartamentoService {

	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Departmento> findAll(){
		return dao.findAll();
	}
}
