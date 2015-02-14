package com.tengen;

import java.util.Arrays;

import com.mongodb.BasicDBObject;

public class DocumentRepresentationTest {
	public static void main(String args[])
	{
		BasicDBObject doc = new BasicDBObject();
		doc.put("userName", "juan");
		doc.put("age", 30);
		doc.put("languages", Arrays.asList("Java", "PHP"));
		doc.put("address", new BasicDBObject("street", "20 Main").append("town", "medellin"));
	}
}
