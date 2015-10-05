package com.example.https

import java.security.{SecureRandom, KeyStore}
import javax.net.ssl.{KeyManagerFactory, SSLContext, TrustManagerFactory}
import spray.io._

trait SslConfiguration {
  // If there is no SSLContext in scope implicitly, the default SSLContext is 
  // going to be used. But we want non-default settings so we are making 
  // custom SSLContext available here.
  implicit def sslContext: SSLContext = {
    val keyStoreResource = "/sample-https-server.jks"
    val password = "change-me-please"

    val keyStore = KeyStore.getInstance("jks")
    keyStore.load(getClass.getResourceAsStream(keyStoreResource), password.toCharArray)
    val keyManagerFactory = KeyManagerFactory.getInstance("SunX509")
    keyManagerFactory.init(keyStore, password.toCharArray)
    val trustManagerFactory = TrustManagerFactory.getInstance("SunX509")
    trustManagerFactory.init(keyStore)
    val context = SSLContext.getInstance("TLS")
    context.init(keyManagerFactory.getKeyManagers, trustManagerFactory.getTrustManagers, new SecureRandom)
    context
  }

  // If there is no ServerSSLEngineProvider in scope implicitly, 
  // the default one is going to be used. But we would like to configure
  // cipher suites and protocols  so we are making a custom ServerSSLEngineProvider
  // available here.
  implicit def sslEngineProvider: ServerSSLEngineProvider = {
    ServerSSLEngineProvider { engine =>
      engine.setEnabledCipherSuites(Array("TLS_RSA_WITH_AES_128_CBC_SHA"))
      engine.setEnabledProtocols(Array( "TLSv1", "TLSv1.1", "TLSv1.2" ))
      engine
    }
  }
}
