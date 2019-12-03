package model.Services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentoDao;
import model.entities.Departamento;



public class DepartamentoService {

	private DepartmentoDao dao = DaoFactory.createDepartmentoDao();
	
	public List<Departamento> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Departamento obj) {
		if(obj .getId() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	//Remover um departamento do banco de dados
	public void remove(Departamento obj) {
		dao.deleteById(obj.getId());
	}
}
