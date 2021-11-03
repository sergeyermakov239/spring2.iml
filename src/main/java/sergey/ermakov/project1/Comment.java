package sergey.ermakov.project1;

import java.util.Date;

public class Comment {
    private String name;
    private String user;
    private Date date;


    public Comment(String name1,String user1){
        name=name1;
        user=user1;
        date=new Date();

    };

    public String getName() {
        return name;
    };

    public String getUser() {
        return user;
    };

    public Date getDate() {
        return date;
    };

    public void setName(String name) {
        this.name = name;
    }
}
