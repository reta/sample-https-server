package com.example.https;

public class HttpsServerRunner {
	public static void main(String[] args) {
		final HttpsServer server = new HttpsServer(RestService$.MODULE$.defaultRoute());
		server.start(10999);
	}
}
