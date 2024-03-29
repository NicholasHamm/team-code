package project.app;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
    @Id
    private int id;

    private String name;

    private String email;
    
    public Users() {}
    
    public Users(int id, String name, String email) {
    	this.id = id;
    	this.name = name;
    	this.email = email;
    }
    
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
