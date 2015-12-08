package com.ibm.cloudoe.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.cloudoe.models.Message;

@Path("/diabetes")
public class DiabetesDetection
{
	@GET
	@Path("{id}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message detect(@PathParam("id") String id, @PathParam("name") String name)
	{
		// Sample REST url:
		// http://dbaas.mybluemix.net/api/diabetes/123/ting
		
		final Message msg = new Message();
		msg.setMsg("Hi " + name + "! " + id);
		
		return msg;
	}
}
