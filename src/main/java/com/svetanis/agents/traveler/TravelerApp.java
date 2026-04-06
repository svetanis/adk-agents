package com.svetanis.agents.traveler;

import com.google.adk.agents.BaseAgent;
import com.google.adk.web.AdkWebServer;

// mvn compile exec:java -Dexec.mainClass=com.svetanis.agents.traveler.TravelerApp

// Suggested PROMPTs: 
// what are the highly-rated pizza restaurants in Florence, Italy?

public class TravelerApp {

	public static void main(String[] args) throws Exception {
		BaseAgent agent = new RestaurantAgent().get();
		// Run your agent with the ADK Dev UI
		AdkWebServer.start(agent);
	}
}
