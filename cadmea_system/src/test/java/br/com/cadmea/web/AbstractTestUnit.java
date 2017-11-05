package br.com.cadmea.web;

import br.com.cadmea.spring.util.SpringFunctions;
import com.google.gson.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartCadmeaSystem.class)
public abstract class AbstractTestUnit {

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected static final String SERVER = "http://localhost:8080/";

    protected MockMvc mockMvc;

    private Gson gson;

    protected MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(final JsonElement json, final Type typeOfT,
                                final JsonDeserializationContext context) throws JsonParseException {
            final DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = writeFormat.parse(json.getAsString());
            } catch (final ParseException e) {
                e.printStackTrace();
            }
            return json == null ? null : date;
        }
    };

    @Before
    public void setup() {
        gson = new GsonBuilder().registerTypeAdapter(Date.class, deser).create();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain).build();
    }

    /**
     * @param entityName
     * @param isProtectedUrl
     * @return the url path as String to create a resource in a rest service
     */
    protected String getCreateUrlForEntity(final String entityName,
                                           final boolean isProtectedUrl) {
        if (!isProtectedUrl) {
            return SERVER + "api/public/" + entityName + "/create/";
        } else {
            return SERVER + "api/private/" + entityName + "/create/";
        }
    }

    /**
     * @param entityName
     * @param isProtectedUrl
     * @return
     */
    protected String getReadUrlForEntity(final String entityName,
                                         final boolean isProtectedUrl) {
        if (!isProtectedUrl) {
            return SERVER + "api/public/" + entityName + "/read/{id}";
        } else {
            return SERVER + "api/private/" + entityName + "/read/{id}";
        }
    }

    /**
     * @param entityName
     * @param isProtectedUrl
     * @return
     */
    protected String getLoadUrlForEntity(final String entityName,
                                         final boolean isProtectedUrl) {
        if (!isProtectedUrl) {
            return SERVER + "api/public/" + entityName + "/load/{id}";
        } else {
            return SERVER + "api/private/" + entityName + "/load/{id}";
        }
    }

    /**
     * @param entityName
     * @param isProtectedUrl
     * @return
     */
    protected String getUpdateUrlForEntity(final String entityName,
                                           final boolean isProtectedUrl) {
        if (!isProtectedUrl) {
            return SERVER + "api/public/" + entityName + "/update/";
        } else {
            return SERVER + "api/private/" + entityName + "/update/";
        }
    }


    @BeforeClass
    public static void init() {

    }

    public void simulateLogin(final String email) {
        SpringFunctions.createHeaders(email, "password");
    }

    public Gson fromGson() {
        return gson;
    }


}
