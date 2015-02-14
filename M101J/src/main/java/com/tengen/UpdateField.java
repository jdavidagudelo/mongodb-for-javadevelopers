package com.tengen;

import java.net.UnknownHostException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class UpdateField {
	public static void main(String args[]) throws UnknownHostException
	{
		MongoClient client = new MongoClient();
		DB couseDB = client.getDB("school");
		DBCollection collection = couseDB.getCollection("students");
		int count = 0;
		DBCursor cursor = collection.find();
		try
		{
		while(cursor.hasNext())
		{
			DBObject object = cursor.next();
			BasicDBList score = (BasicDBList)object.get("scores");
			DBObject min = null;
			Double minValue = Double.MAX_VALUE;
			System.out.println(object.get("_id"));
			System.out.println(score);
			for(Object o : score.toArray())
			{
				DBObject ob = (DBObject)o;
				if(ob.get("type").equals("homework")){
					Double value = Double.parseDouble(ob.get("score").toString());
					if(minValue > value)
					{
						minValue = value;
						min = ob;
					}
					System.out.println(ob);
				}
			}
			System.out.println(min);
			
			score.remove(min);
			DBObject set = new BasicDBObject("$set", new BasicDBObject("scores", score));
			System.out.println(set);
			DBObject findUpdate = new BasicDBObject("_id", object.get("_id"));
			collection.update(findUpdate, set);
			//System.out.println(object);
				//System.out.println(currentId);
				//currentId = String.valueOf(object.get("student_id"));
				//collection.remove(object);
				count++;
			//System.out.println(object);
			//collection.remove(object);
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
