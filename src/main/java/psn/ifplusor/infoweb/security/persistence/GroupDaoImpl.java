package psn.ifplusor.infoweb.security.persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository("GroupDao")
public class GroupDaoImpl implements GroupDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Group getGroupByName(String groupname) {
        String jpql = "from Group g where g.groupname=?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1, groupname);

        try {
            return (Group) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nure) {
            return null;
        } catch (IllegalStateException ise) {
            return null;
        } catch (QueryTimeoutException qte) {
            return null;
        } catch (TransactionRequiredException tre) {
            return null;
        } catch (PessimisticLockException ple) {
            return null;
        } catch (LockTimeoutException lte) {
            return null;
        } catch (PersistenceException pe) {
            return null;
        }
    }

    @Override
    public boolean addUserToGroup(Group group, User user) {
        group.getUsers().add(user);
        return true;
    }
}
