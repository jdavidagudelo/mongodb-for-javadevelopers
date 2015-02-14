package com.tengen;

import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Week1Homework4 {
    private static final Logger logger = LoggerFactory.getLogger("logger");

    public static void main(String[] args) throws UnknownHostException {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                Week1Homework4.class, "/");

MongoClient client = new MongoClient(new MongoClientURI("mongodb://jdavidagudelo:namadaju1037524435@ds043981.mongolab.com:43981/heroku_app33880272"));
		
        DB database = client.getDB("heroku_app33880272");
        final DBCollection collection = database.getCollection("funnynumbers");

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request,
                                 final Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("answer.ftl");

                    // Not necessary yet to understand this.  It's just to prove that you
                    // are able to run a command on a mongod server
                    @SuppressWarnings("deprecation")
					AggregationOutput output =
                            collection.aggregate(
                                    new BasicDBObject("$group",
                                            new BasicDBObject("_id", "$value")
                                                    .append("count", new BasicDBObject("$sum", 1)))
                                    ,
                                    new BasicDBObject("$match", new BasicDBObject("count",
                                            new BasicDBObject("$lte", 2))),
                                    new BasicDBObject("$sort", new BasicDBObject("_id", 1))
                            );

                    int answer = 0;
                    for (DBObject doc : output.results()) {
                        answer += (Integer) doc.get("_id");
                    }

                    Map<String, String> answerMap = new HashMap<String, String>();
                    answerMap.put("answer", Integer.toString(answer));
                    System.out.println(answer);
                    helloTemplate.process(answerMap, writer);
                } catch (Exception e) {
                    logger.error("Failed", e);
                    e.printStackTrace();
                    halt(500);
                }
                return writer;
            }
        });
    }
}