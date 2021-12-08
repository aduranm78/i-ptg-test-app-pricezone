package com.redhat;

//import com.redhat.dto.Customer;
//import com.redhat.dto.CustomerSuccess;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty.http.NettyHttpMessage;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.apache.camel.LoggingLevel;

import org.apache.camel.Processor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import java.util.Map;

@Component
public class Routes extends RouteBuilder {

  String erpBase = "";
  String NSUri = "";
  String queryBase = "";
  //System.out.println("URL OAuth:"+ erpOAuth);

  @Override
  public void configure() throws Exception {

    erpBase = "https://5298967-sb1.restlets.api.netsuite.com/app/site/hosting/restlet.nl?";
    queryBase = "script=1019&deploy=1";

    restConfiguration()
      .component("netty-http")
      .port("8080")
      .bindingMode(RestBindingMode.auto);

    rest()
      .path("/").consumes("application/json").produces("application/json")
        .put("/get-zones")
//          .type(Customer.class).outType(CustomerSuccess.class)
          .to("direct:put-customer")
        .post("/get-zones")
//          .type(Customer.class).outType(CustomerSuccess.class)
          .to("direct:post-customer")
        .get("/get-zones")
  //          .type(Customer.class).outType(CustomerSuccess.class)
          .to("direct:get-customer");
    
    from("direct:post-customer")
      .setHeader("HTTP_METHOD", constant("POST"))
      .to("direct:request");
    from("direct:put-customer")
      .setHeader("HTTP_METHOD", constant("PUT"))
      .to("direct:request");
    from("direct:get-customer")
      .setHeader("HTTP_METHOD", constant("GET"))
      .to("direct:request");

    from("direct:request")
      .process(new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
          String erpOAuth= "";
          System.out.println("erpOAuth:"+erpOAuth);
          Message inMessage = exchange.getIn();
          String query = inMessage.getHeader(Exchange.HTTP_QUERY, String.class);
          System.out.println("Query:"+query);
          if(query != null){
              erpOAuth = erpBase + "" + queryBase + "&" +query;
              query = queryBase + "&" +query;
              System.out.println("Query is not null:"+query);
          }else{
            erpOAuth = erpBase + "" + queryBase;
              query = queryBase;
          }
          String authHeader = OAuthSign.getAuthHeader(erpOAuth,"GET");
          exchange.getMessage().setHeader("Authorization", authHeader);
          //System.out.println("header process:"+authHeader);
          NSUri = erpOAuth + "&bridgeEndpoint=true&throwExceptionOnFailure=false";
          System.out.println("NSUri:"+NSUri);
          exchange.getMessage().setHeader("CamelHttpRawQuery", query);
          System.out.println("Query:"+query);
          exchange.getMessage().setHeader(Exchange.HTTP_URI, NSUri);
          System.out.println("URI:"+NSUri);
        }
      })
      .to("log:DEBUG?showBody=true&showHeaders=true")
      .to("https://netsuite")
      .streamCaching()
      .log(LoggingLevel.INFO, "${in.headers.CamelFileName}")
      .to("log:DEBUG?showBody=true&showHeaders=true")
      .removeHeaders("*");
      
//      .choice()
//        .when(simple("${header.CamelHttpResponseCode} != 201 && ${header.CamelHttpResponseCode} != 202"))
//          .log("err")
//          .transform(constant("Error"))
//        .otherwise()
//          .log("ok")
//      .endChoice();
  }
}