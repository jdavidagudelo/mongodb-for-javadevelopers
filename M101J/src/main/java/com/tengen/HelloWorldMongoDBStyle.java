package com.tengen;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class HelloWorldMongoDBStyle {
	public static void main(String args[]) throws UnknownHostException{
	
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://jdavidagudelo:namadaju1037524435@ds043981.mongolab.com:43981/heroku_app33880272"));
		
		DB database = client.getDB("heroku_app33880272");
		DBCollection collection = database.getCollection("hello");
		DBCursor cursor = collection.find();
		while(cursor.hasNext())
		{
			System.out.println(cursor.next());
		}
		//System.out.println(document);
	}
}
