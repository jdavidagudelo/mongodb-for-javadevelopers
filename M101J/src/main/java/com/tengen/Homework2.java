package com.tengen;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Homework2 {
	public static void main(String args[]) throws UnknownHostException
	{
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://jdavidagudelo:namadaju1037524435@ds043981.mongolab.com:43981/heroku_app33880272"));
		
		DB database = client.getDB("heroku_app33880272");
		DBCollection collection = database.getCollection("grades");
		DBObject sort = new BasicDBObject("student_id", 1).append("score", 1);
		DBObject find = new BasicDBObject("type", "homework");

		int count = 0;
		DBCursor cursor = collection.find(find).sort(sort);
		try
		{
		String currentId = "";
		while(cursor.hasNext())
		{
			DBObject object = cursor.next();
			if(!String.valueOf(object.get("student_id")).equals(currentId))			{
				currentId = String.valueOf(object.get("student_id"));
				collection.remove(object);
				count++;
			}
			//System.out.println(object);
		}
		}
		finally
		{
			cursor.close();
		}
		System.out.println(count);
		
	}
}
