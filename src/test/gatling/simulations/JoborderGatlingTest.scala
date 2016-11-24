import java.nio.charset.StandardCharsets
import java.util.Base64

import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Joborder entity.
 */
class JoborderGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val authorization_header = "Basic " + Base64.getEncoder.encodeToString("hireapp:my-secret-token-to-change-in-production".getBytes(StandardCharsets.UTF_8))

    val headers_http_authentication = Map(
        "Content-Type" -> """application/x-www-form-urlencoded""",
        "Accept" -> """application/json""",
        "Authorization"-> authorization_header
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "Bearer ${access_token}"
    )

    val scn = scenario("Test the Joborder entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/oauth/token")
        .headers(headers_http_authentication)
        .formParam("username", "admin")
        .formParam("password", "admin")
        .formParam("grant_type", "password")
        .formParam("scope", "read write")
        .formParam("client_secret", "my-secret-token-to-change-in-production")
        .formParam("client_id", "hireapp")
        .formParam("submit", "Login")
        .check(jsonPath("$.access_token").saveAs("access_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all joborders")
            .get("/api/joborders")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new joborder")
            .post("/api/joborders")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "joborderId":"0", "recruiter":"0", "contactId":"0", "companyId":"0", "enteredBy":"0", "owner":"0", "siteId":"0", "clientJobId":"SAMPLE_TEXT", "title":"SAMPLE_TEXT", "description":"SAMPLE_TEXT", "notes":"SAMPLE_TEXT", "type":"SAMPLE_TEXT", "duration":"SAMPLE_TEXT", "rateMax":"SAMPLE_TEXT", "salary":"SAMPLE_TEXT", "status":"SAMPLE_TEXT", "isHot":"0", "openings":"0", "city":"SAMPLE_TEXT", "state":"SAMPLE_TEXT", "startDate":"2020-01-01T00:00:00.000Z", "dateCreated":"2020-01-01T00:00:00.000Z", "dateModified":"2020-01-01T00:00:00.000Z", "publicV":"0", "companyDepartmentId":"0", "isAdminHidden":"0", "openingsAvailable":"0", "questionnaireId":"0"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_joborder_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created joborder")
                .get("${new_joborder_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created joborder")
            .delete("${new_joborder_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
