package repositorio;

import java.util.List;

import modelos.Opinion;

public interface IRepositorioOpinion {
	
	String create(Opinion opinion);
    
	Opinion findById(String id);
    
	List<Opinion> findAll();
    
	void update(Opinion opinion);
    
	void delete(String id);
}
