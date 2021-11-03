package sergey.ermakov.project1;

import java.util.ArrayList;
import java.util.Date;

public class Topic {
    private ArrayList<Comment> comments;
    private String name;
    private Date date;


    public Topic(String name1){
        name=name1;
        comments=new ArrayList<>();
        date =new Date();

    };

    public void delete (int index){
        comments.remove((int) index);
    };

    public void add(Comment comment){
        comments.add(comment);
    }

    public void update (int index,String text){
        String user1=comments.get(index).getUser();
        comments.remove(index);
        comments.add(index,new Comment(text,user1));
    };


    public String printTopic(){
        return name;
    };

    public ArrayList<Comment> getComments() {
        return comments;
    };

    public Date getDate() {
        return date;
    };
}
