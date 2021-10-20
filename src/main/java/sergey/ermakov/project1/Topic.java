package sergey.ermakov.project1;

import java.util.ArrayList;

public class Topic {
    private ArrayList<String> comments;
    private String name;

    public Topic(String name1,ArrayList<String> comments1){
        name=name1;
        comments=comments1;
    };

    public void delete (int index){
        comments.remove((int) index);
    };

    public void add(String text){
        comments.add(text);
    }

    public void update (int index,String text){
        comments.remove((int) index);
        comments.add(index,text);
    };

    public ArrayList<String> printComments (){
        return comments;
    };

    public String printTopic(){
        return name;
    };

    public ArrayList<String> getComments() {
        return comments;
    };
}
