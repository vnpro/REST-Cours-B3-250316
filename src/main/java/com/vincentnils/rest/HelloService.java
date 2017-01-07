package com.vincentnils.rest;

import com.vincentnils.services.DisplayReferencedCodeService;
import com.vincentnils.services.HttpRequestSender;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

// Pour la version @Path depuis navigateur :
//		String requested = "";
//		try {
//			// Encode les whitespace etc... pour LUIS
//			requested = URLEncoder.encode(request, "UTF-8");
//				} catch (UnsupportedEncodingException ignored) {}

@Path("/smartphone")
public class HelloService {
	@GET
	public Response getHello() {
		return Response.status(200).entity("Hello World ! Service online").build();
	}

	// http://localhost:8080/REST-Cours-B3-250316 (HP OLD)
	// http://localhost:9090/hello/luis (ALIEN)

	@GET
	@Path("/{request}")
	public Response getRequest(@PathParam("request") String request) {
		HttpRequestSender client = new HttpRequestSender();
		String response = "";

		if (request.isEmpty())
			return Response.status(200).entity("EmptyRequest").build();

		// Préparation de la requête pour envoi à LUIS
		// Penser à update la requête après un training
 		String luisRequest = "https://api.projectoxford.ai/luis/v2.0/apps/439b46c5-21b9-4ca4-8a62-463de1082251?subscription-key=2c9728fb2ec94a51a0c2895e00eff344&q="+request+"&verbose=true";

		try {
			System.out.println("Envoi à LUIS "+luisRequest);
			response = client.sendGet(luisRequest);
			System.out.println("LUIS response raw : "+response);

			JSONObject jsonObject = new JSONObject(response);

			if (!jsonObject.getString("topScoringIntent").isEmpty()) {
				System.out.println("TopIntentRaw = " + jsonObject.getJSONObject("topScoringIntent"));

				JSONObject top = jsonObject.getJSONObject("topScoringIntent");

				switch (top.getString("intent")) {
					case "DisplayReferencedCode":
						String url = DisplayReferencedCodeService.getInstance().display(jsonObject);
						if (url.equals("404")) {
							response = "404";
						}
						else {
							response = url;
						}
						System.out.println("sending:" + url);
						break;
					default:
						response = "404";
						break;
				}
			}
			else {
				response = "Sorry, i have no intent for what you wan't me to do";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(200).entity("LUIS ERROR").build();
		}

		return Response.status(200).entity(response).build();
	}
}
