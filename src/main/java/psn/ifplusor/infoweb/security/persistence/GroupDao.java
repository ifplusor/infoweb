package psn.ifplusor.infoweb.security.persistence;

public interface GroupDao {

    Group getGroupByName(String groupname);

    boolean addUserToGroup(Group group, User user);
}
