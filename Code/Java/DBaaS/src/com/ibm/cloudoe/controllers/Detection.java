package com.ibm.cloudoe.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.ibm.cloudoe.models.Message;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

@Path("/total")
public class Detection {
	
	@GET
	@Path("{university}/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message detect(@PathParam("university") String university, @PathParam("year") String year)
	{
		// Sample REST url:
		// http://dbaas.mybluemix.net/api/total/umkc/2010
		
		final Message msg = new Message();
		
		JSONObject params = new JSONObject();
		params.put("university", university);
	    params.put("year", year);

		
	    BasicDBObject user1 = new BasicDBObject(params);
		
		for (Object key : params.keySet().toArray()){
			user1.put(key.toString(), params.get(key));
		}
		
		MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds035014.mongolab.com:35014/sampledb");
		MongoClient client = new MongoClient(uri);
		
		DB db = client.getDB(uri.getDatabase());		
		DBCollection collect = db.getCollection("sample");
		
		BasicDBObject query = new BasicDBObject();
		query.put("Year_School_ID", year+"_"+university);
		//query.put("year", year);
		
		DBCursor docs = collect.find(query);
		
		System.out.println(user1);
		
		msg.setMsg(docs.toArray().toString());
		
	    
		return msg;
	}
}
