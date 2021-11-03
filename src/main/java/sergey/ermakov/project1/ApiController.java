package sergey.ermakov.project1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


@RestController
public class ApiController {
    private ArrayList<Topic> topics=new ArrayList<>();


    //curl -X POST http://localhost:8080/topics -H 'Content-Type: application/json' -d 'text'
    @PostMapping("topics")
    public void addTopic(@RequestBody String text){
        topics.add(new Topic(text));
    };
    //curl -X DELETE http://localhost:8080/topics/1
    @DeleteMapping("topics/{index}")
    public void deleteTopic (@PathVariable("index") Integer index){
        topics.remove((int) index);
    };

    @GetMapping("topics/{index}")
    public String getTopic(@PathVariable("index") Integer index){
        return topics.get((int) index).printTopic();
    };

    @GetMapping("topics")
    public ArrayList<String> getTopics(){
        ArrayList<String> a=new ArrayList<>();
        for (Topic top:topics){
            a.add(top.printTopic());
        };
        return a;
    };
    //curl -X PUT http://localhost:8080/topics/2 -H 'Content-Type: application/json' -d 'text10'
    @PutMapping("topics/{index}")
    public void updateTopic (@PathVariable("index") Integer index, @RequestBody String text){
        topics.remove((int) index);
        topics.add(index,new Topic(text));
    };
    @GetMapping("topics/count")
    public int countTopics(){
        return topics.size();
    };
    //curl -X DELETE http://localhost:8080/topics/all
    @DeleteMapping("topics/all")
    public void deleteTopics (){
        topics.clear();
    };





    //curl -X POST http://localhost:8080/topics/1/comments -H 'Content-Type: application/json' -d '{"name1":"comment1","user1":"Sergey"}'
    @PostMapping("topics/{index}/comments")
    public void addComment (@PathVariable("index") Integer index,@RequestBody Comment comment ){
        topics.get((int)index).add(comment);
    };
    //curl -X DELETE http://localhost:8080/topics/1/2/comments
    @DeleteMapping("topics/{index1}/{index2}/comments")
    public void deleteComment (@PathVariable("index1") Integer index1,@PathVariable("index2") Integer index2){
        topics.get((int) index1).delete(index2);
    };
    //curl -X PUT http://localhost:8080/topics/2/5/comments -H 'Content-Type: application/json' -d 'comment123'
    @PutMapping("topics/{index1}/{index2}/comments")
    public void updateComment(@PathVariable("index1") Integer index1,@PathVariable("index2") Integer index2,@RequestBody String text){
        topics.get((int) index1).update(index2,text);
    };
    @GetMapping("topics/{index}/comments")
    public ArrayList<Comment> getComments(@PathVariable("index") Integer index){
        return topics.get((int) index).getComments();

    };




    @GetMapping("topics/users/{user}")
    public ArrayList<Comment> getCommentsofUser(@PathVariable("user") String user){
        ArrayList<Comment> a=new ArrayList<>();
        for (Topic t:topics){
            for (Comment c:t.getComments()){
                if (c.getUser().equals(user)){
                    a.add(c);
                }
            }
        };
        return a;
    };
    //curl -X PUT http://localhost:8080/topics/users/1/Sergey -H 'Content-Type: application/json' -d 'comment123'
    @PutMapping("topics/users/{index}/{user}")
    public void updateCommentofUser(@PathVariable("index") Integer index,@PathVariable("user") String user,@RequestBody String text){
        boolean f=true;
        for (int i=0;i<topics.get(index).getComments().size()&&f;i++){
            if (topics.get(index).getComments().get(i).getUser().equals(user)){
                f=false;
                topics.get(index).update(i,text);
            }
        }
    };
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



    //curl -X GET http://localhost:8080/topics/sort/1?sort=up
    @GetMapping("topics/sort/{index}")
    public ArrayList<Comment> getSortedComments(@PathVariable("index") Integer index,@RequestParam("sort") String sort){
        Collections.sort(topics.get(index).getComments(), new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                if (sort.equals("up")){
                    if (o1.getDate().after(o2.getDate())){
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (o1.getDate().after(o2.getDate())){
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
        return topics.get(index).getComments();
    };
    //curl -X GET http://localhost:8080/topics/sort?sort=up
    @GetMapping("topics/sort")
    public ArrayList<String> getSortedTopics(@RequestParam("sort") String sort){
        Collections.sort(topics, new Comparator<Topic>() {
            @Override
            public int compare(Topic o1, Topic o2) {
                if (sort.equals("up")){
                    if (o1.getDate().after(o2.getDate())){
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (o1.getDate().after(o2.getDate())){
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
        ArrayList<String> a=new ArrayList<>();
        for (Topic t:topics){
            a.add(t.printTopic());
        };
        return a;
    }

}

