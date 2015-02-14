package com.tengen;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HelloWorldMongoDBSparkFreeMarker {
	public static void main(String args[]) throws UnknownHostException
	{
		MongoClient client = new MongoClient();
		DB database = client.getDB("hello");
		final DBCollection collection = database.getCollection("hello");
		
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldFreeMarkerStyle.class, "/");
		
		Spark.get(new Route("/") {
			
			@Override
			public Object handle(Request arg0, Response arg1) {
				// TODO Auto-generated method stub
				Template template = null;
				try {
					template = configuration.getTemplate("hello.ftl");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				StringWriter writer = new StringWriter();
				/**Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", "Juan");*/
				DBObject document = collection.findOne();
				try {
					template.process(document, writer);
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(writer);
				return writer.toString();
			}
		});
	}
}
