package com.ibm.cloudoe.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.cloudoe.models.Credential;
import com.ibm.cloudoe.models.User;

@Path("/users")
public class UserController
{
	private static final Gson gson = new Gson();

	private static final String MONGOLAB_URL_FORMAT = "https://api.mongolab.com/api/1/databases/%s/collections/%s?%sapiKey=%s";
	
	private static final String MONGOLAB_CRU_API = String.format(
		MONGOLAB_URL_FORMAT,
		"mysql",                           // Name of your database
		"users",                           // Name of your collections
		"",                                // Document id retrieved from _id.$oid used only for Delete
		"V9wNk7IJhJCX3tA-PczYNw3iR2JtdNhi" // Your token ID to access mongolab data API more info read http://docs.mongolab.com/data-api/
	);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User post(User user)
	{
		final HttpClient httpClient = HttpClientBuilder.create().build();
		final HttpPost httpPost = new HttpPost(MONGOLAB_CRU_API);
		httpPost.setHeader("Content-type", "application/json");
		
		try {
			final StringEntity entity = new StringEntity(gson.toJson(user));
			httpPost.setEntity(entity);
			
			httpClient.execute(httpPost);
			
			return user;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/identify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> get(Credential credential) throws UnsupportedEncodingException
	{
		if (credential.getEmail().trim().isEmpty() || credential.getPassword().trim().isEmpty()) {
			return Collections.emptyList();
		}
		
		final String fetchUrl = String.format(
				MONGOLAB_URL_FORMAT,
				"mysql", "users",
				"q=" + URLEncoder.encode(gson.toJson(credential), "ISO-8859-1") + "&",
				"V9wNk7IJhJCX3tA-PczYNw3iR2JtdNhi"
		);
		
		System.out.println(fetchUrl);
		
		final HttpClient httpClient = HttpClientBuilder.create().build();
		final HttpGet httpGet = new HttpGet(fetchUrl);
		httpGet.setHeader("Content-type", "application/json");
		
		try {			
			final HttpResponse httpResponse = httpClient.execute(httpGet);
			final String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			System.out.println(responseString);

			return gson.fromJson(responseString, new TypeToken<List<User>>(){}.getType());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User put(User user)
	{
		// TODO implement update user profile
		
		return null;
	}
	
	@DELETE
	@Path("{docId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User delete(@PathParam("docId") String docId)
	{
		// TODO implement delete user profile
		
		return null;
	}
}
