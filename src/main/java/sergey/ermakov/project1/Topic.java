package sergey.ermakov.project1;

import java.util.ArrayList;
import java.util.Date;

public class Topic {
    private ArrayList<Comment> comments;
    private String name;
    private Date date;
    private Date dateofupdate;


    public Topic(String name1){
        name=name1;
        comments=new ArrayList<>();
        date =new Date();
        dateofupdate=new Date();

    };

    public void delete (int index){
        if (index>=0&&index<comments.size()){
        comments.remove((int) index);
        }
    };

    public void add(Comment comment){
        comments.add(comment);
    }

    public void update (int index,String text){
        if(index>=0&&index<comments.size()) {
            comments.get(index).update(text);
            dateofupdate=new Date();
        }
    };

    public void updateName (String text){
        name=text;
        dateofupdate=new Date();
    }


    public String printTopic(){
        return name;
    };

    public ArrayList<Comment> getComments() {
        return comments;
    };

    public Date getDate() {
        return date;
    };

    public Date getDateofupdate() {
        return dateofupdate;
    };

    public void setDateofupdate(Date dateofupdate) {
        this.dateofupdate = dateofupdate;
    }
}
