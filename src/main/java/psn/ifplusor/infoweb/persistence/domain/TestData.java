package psn.ifplusor.infoweb.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_data")
public class TestData implements java.io.Serializable {
	
	private static final long serialVersionUID = 0L;

    private String name;
    
    public TestData() {
    	super();
    	System.out.println("make an test_data!");
    }

    @Id
	@Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
