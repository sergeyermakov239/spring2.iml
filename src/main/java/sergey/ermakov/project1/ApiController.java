package sergey.ermakov.project1;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


@RestController
public class ApiController {
    private ArrayList<Topic> topics=new ArrayList<>();


    //curl -X POST http://localhost:8080/topics -H 'Content-Type: application/json' -d 'text'
    @PostMapping("topics")
    public void addTopic(@RequestBody String text){
        topics.add(new Topic(text));
    }
    //curl -X DELETE http://localhost:8080/topics/1
    @DeleteMapping("topics/{index}")
    public void deleteTopic (@PathVariable("index") Integer index){
        if(index>=0&&index<topics.size()) {
            topics.remove((int) index);
        }
    }

    @GetMapping("topics/{index}")
    public String getTopic(@PathVariable("index") Integer index){
        if(index>=0&&index<topics.size()) {
            return topics.get( index).printTopic();
        } else{
            return null;
        }
    }

    @GetMapping("topics")
    public ArrayList<String> getTopics(){
        ArrayList<String> a=new ArrayList<>();
        for (Topic top:topics){
            a.add(top.printTopic());
        }
        return a;
    }
    //curl -X PUT http://localhost:8080/topics/2 -H 'Content-Type: application/json' -d 'text10'
    @PutMapping("topics/{index}")
    public void updateTopic (@PathVariable("index") Integer index, @RequestBody String text){
        if(index>=0&&index<topics.size()) {
            topics.get(index).updateName(text);
        }
    }
    @GetMapping("topics/count")
    public int countTopics(){
        return topics.size();
    }
    //curl -X DELETE http://localhost:8080/topics/all
    @DeleteMapping("topics/all")
    public void deleteTopics (){
        topics.clear();
    }





    //curl -X POST http://localhost:8080/topics/1/comments -H 'Content-Type: application/json' -d '{"name1":"comment1","user1":"Sergey"}'
    @PostMapping("topics/{index}/comments")
    public void addComment (@PathVariable("index") Integer index,@RequestBody Comment comment ){
        if(index>=0&&index<topics.size()) {
            topics.get( index).add(comment);
        }
    }
    //curl -X DELETE http://localhost:8080/topics/1/2/comments
    @DeleteMapping("topics/{index1}/{index2}/comments")
    public void deleteComment (@PathVariable("index1") Integer index1,@PathVariable("index2") Integer index2){
        if ((index1>=0&&index1<topics.size())&&(index2>=0&&index2<topics.get(index1).getComments().size())) {
            topics.get( index1).delete(index2);
        }
    }
    //curl -X PUT http://localhost:8080/topics/2/5/comments -H 'Content-Type: application/json' -d 'comment123'
    @PutMapping("topics/{index1}/{index2}/comments")
    public void updateComment(@PathVariable("index1") Integer index1,@PathVariable("index2") Integer index2,@RequestBody String text){
        if ((index1>=0&&index1<topics.size())&&(index2>=0&&index2<topics.get(index1).getComments().size())) {
            topics.get( index1).update(index2, text);
            topics.get(index1).setDateofupdate(new Date());
        }
    }
    @GetMapping("topics/{index}/comments")
    public ArrayList<Comment> getComments(@PathVariable("index") Integer index){
        if (index>=0&&index<topics.size()) {
            return topics.get( index).getComments();
        } else{
            return null;
        }

    }




    @GetMapping("topics/users/{user}")
    public ArrayList<Comment> getCommentsofUser(@PathVariable("user") String user){
        ArrayList<Comment> a=new ArrayList<>();
        for (Topic t:topics){
            for (Comment c:t.getComments()){
                if (c.getUser().equals(user)){
                    a.add(c);
                }
            }
        }
        if (a.size()>0)  {
        return a;
        } else{
            return null;
        }
    }
    //curl -X PUT http://localhost:8080/topics/users/1/Sergey -H 'Content-Type: application/json' -d 'comment123'
    @PutMapping("topics/users/{index}/{user}")
    public void updateCommentofUser(@PathVariable("index") Integer index,@PathVariable("user") String user,@RequestBody String text){
        boolean f=true;
        if (index>=0&&index<topics.size()) {
            for (int i = 0; i < topics.get(index).getComments().size() && f; i++) {
                if (topics.get(index).getComments().get(i).getUser().equals(user)) {
                    f = false;
                    topics.get(index).update(i, text);
                }
            }
        }
    }
    //curl -X DELETE http://localhost:8080/topics/users/Sergey
    @DeleteMapping("topics/users/{user}")
    public void deleteCommentsofUser(@PathVariable("user") String user){
        for (Topic t:topics){
            for (int i=t.getComments().size()-1;i>=0;i=i-1){
                if (t.getComments().get(i).getUser().equals(user)){
                    t.delete(i);
                }
            }
        }
    }



    //curl -X GET 'http://localhost:8080/topics/sort/1?sort=up&sort2=date'
    @GetMapping("topics/sort/{index}")                     //параметр sort отвечает за направление сортировки (up,down) ; параметр sort2 отвечает за вид сортировки (date, name)
    public ArrayList<Comment> getSortedComments(@PathVariable("index") Integer index,@RequestParam("sort") String sort, @RequestParam("sort2") String sort2){
        if (index>=0&&index<topics.size()) {
            Collections.sort(topics.get(index).getComments(), new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    if (sort.equals("up")) {
                        if (sort2.equals("date")) {
                            if (o1.getDateofupdate().after(o2.getDateofupdate())) {
                                return 1;
                            } else {
                                return -1;
                            }
                        } else {
                            if (o1.getName().compareTo(o2.getName()) > 0) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    } else {
                        if (sort2.equals("date")) {
                            if (o1.getDateofupdate().after(o2.getDateofupdate())) {
                                return -1;
                            } else {
                                return 1;
                            }
                        } else {
                            if (o1.getName().compareTo(o2.getName()) > 0) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    }
                }
            });
            return topics.get(index).getComments();
        } else{
            return null;
        }
    }
    //curl -X GET 'http://localhost:8080/topics/sort?sort=up&sort2=date'
    @GetMapping("topics/sort")                 //параметр sort отвечает за направление сортировки (up,down) ; параметр sort2 отвечает за вид сортировки (date, name)
    public ArrayList<String> getSortedTopics(@RequestParam("sort") String sort, @RequestParam("sort2") String sort2){
        Collections.sort(topics, new Comparator<Topic>() {
            @Override
            public int compare(Topic o1, Topic o2) {
                if (sort.equals("up")){
                    if (sort2.equals("date")) {
                        if (o1.getDateofupdate().after(o2.getDateofupdate())) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else{
                        if (o1.printTopic().compareTo(o2.printTopic())>0) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                } else {
                    if (sort2.equals("date")) {
                        if (o1.getDateofupdate().after(o2.getDateofupdate())) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } else{
                        if (o1.printTopic().compareTo(o2.printTopic())>0) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                }
            }
        });
        ArrayList<String> a=new ArrayList<>();
        for (Topic t:topics){
            a.add(t.printTopic());
        }
        return a;
    }

}

