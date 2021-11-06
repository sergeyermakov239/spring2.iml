package sergey.ermakov.project1;

import java.util.Date;

public class Comment {
    private String name;
    private String user;
    private Date date;
    private Date dateofupdate;


    public Comment(String name1,String user1){
        name=name1;
        user=user1;
        date=new Date();
        dateofupdate=new Date();

    };

    public String getName() {
        return name;
    };

    public void update(String text){
        name=text;
        dateofupdate=new Date();
    }

    public String getUser() {
        return user;
    };

    public Date getDate() {
        return date;
    };

    public Date getDateofupdate() {
        return dateofupdate;
    }

    public void setName(String name) {
        this.name = name;
    }
}
