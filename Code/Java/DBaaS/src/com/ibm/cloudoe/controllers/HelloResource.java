package com.ibm.cloudoe.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/hello")
public class HelloResource
{
	@GET
	public String getInformation() {

		return "Hi Tarun Shedhani!";
	}
}