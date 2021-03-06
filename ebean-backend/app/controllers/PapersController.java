package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Paper;
import models.User;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.util.Locale;

public class PapersController extends Controller {

    public Result test() throws IOException {
//        whitespaceAnalyzer to parse input text, tokenize text into word tokens
        Analyzer analyzer = new WhitespaceAnalyzer();
//        create in-memory directory
        Directory directory = new RAMDirectory();
//        configure indexwriter with whitespaceAnalyzr
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//        pass directory and indexwriter-configuration to create index writer
        IndexWriter indexWriter = new IndexWriter(directory, config);
        return ok();
    }

    /*BELOW IS FUNCTIONAL IMPLEMENTATION WITHOUT APACHE LUCENE*/
    public Result search() {

        JsonNode req = request().body().asJson();

        String title = req.get("title").asText().toLowerCase(Locale.ROOT);
        String author = req.get("author").asText().toLowerCase(Locale.ROOT);
        String topic = req.get("topic").asText().toLowerCase(Locale.ROOT);

//        json-string is "" when there is no input
        if (title != "") {
            return findByTitle(title);
        }
        else if(author != ""){
            return findByAuthor(author);
        }
        else if(topic != ""){
            return findByTopic(topic);
        }
        else{
            return ok("no search result...");
        }
    }
    public Result findByTitle(String title){
        try {
            Paper paper = Paper.findByTitle(title);

            if (paper != null) {
                JsonNode jsonResult = Json.toJson(paper);
                System.out.println(jsonResult);
                return ok(jsonResult);
            } else {
                return ok("not found");
            }
        } catch (Exception e) {
            return ok("false");
        }
    }

    public Result findByAuthor(String author){
        try {
            System.out.println(author);
            Paper paper = Paper.findByAuthor(author);

            if (paper != null) {
                JsonNode jsonResult = Json.toJson(paper);
                return ok(jsonResult);
            } else {
                System.out.println("author not found");
                return ok("not found");
            }
        } catch (Exception e) {
            return ok("false");
        }
    }

    public Result findByTopic(String topic){
        try {
            System.out.println(topic);
            Paper paper = Paper.findByTopic(topic);

            if (paper != null) {
                System.out.println("FOUND TOPIC");
                JsonNode jsonResult = Json.toJson(paper);
                return ok(jsonResult);
            } else {
                return ok("not found");
            }
        } catch (Exception e) {
            return ok("false");
        }
    }
}