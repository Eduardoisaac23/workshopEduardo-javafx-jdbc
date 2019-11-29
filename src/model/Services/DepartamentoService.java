package model.Services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Departamento;

public class DepartamentoService {

	public List<Departamento> findAll(){
		List<Departamento> list = new ArrayList<>();
		list.add(new Departamento(1, "Books"));
		list.add(new Departamento(2, "Computador"));
		list.add(new Departamento(3, "Eletrônicos"));
		list.add(new Departamento(4, "Ti"));
		list.add(new Departamento(5, "RH"));
		return list;
	}
}
