package psn.ifplusor.infoweb.cms.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class VirtualFilesystemDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Folder getTopFolder() {
		String jpql = "from Folder f where f.id=1";
		Query query = entityManager.createQuery(jpql);
		
		Folder folder = (Folder) query.getSingleResult();
		
		return folder;
	}
	
	public void saveFolder(Folder folder) {
		
		entityManager.persist(folder);
	}
	
	public void deleteFolder(Folder folder) {
		
		entityManager.remove(folder);
	}
	
	public void deleteFile(File file) {
		
		entityManager.remove(file);
	}
}
