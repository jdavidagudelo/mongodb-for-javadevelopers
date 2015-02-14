package com.tengen;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;

public class Week1Homework3 {
    public static void main(String[] args) throws UnknownHostException {
    	MongoClient client = new MongoClient(new MongoClientURI("mongodb://jdavidagudelo:namadaju1037524435@ds043981.mongolab.com:43981/heroku_app33880272"));
		
        DB database = client.getDB("heroku_app33880272");
        DBCollection collection = database.getCollection("funnynumbers");

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
                                new BasicDBObject("$gt", 2))),
                        new BasicDBObject("$sort", new BasicDBObject("_id", 1))
                );

        int answer = 0;
        for (DBObject doc : output.results()) {
            answer += (Integer) doc.get("_id");
        }

        System.out.println("THE ANSWER IS: " + answer);
    }
}