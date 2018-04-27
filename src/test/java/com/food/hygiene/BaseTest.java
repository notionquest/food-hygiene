package com.food.hygiene;

import com.food.hygiene.model.Establishments;
import com.food.hygiene.util.JsonUtils;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected String getLocalAuthoritiesJsonFromFile() throws IOException {
        String localAuthoritiesJson = new String(FileCopyUtils.copyToByteArray(BaseTest.class.getResourceAsStream("/local_authorties.json")));
        return localAuthoritiesJson;
    }

    protected String getScoreDescriptorsJsonFromFile() throws IOException {
        String scoreDescriptors = new String(FileCopyUtils.copyToByteArray(BaseTest.class.getResourceAsStream("/score_descriptors.json")));
        return scoreDescriptors;
    }


    protected String getEstablishmentsJsonFromFile() throws IOException {
        String establishments = new String(FileCopyUtils.copyToByteArray(BaseTest.class.getResourceAsStream("/establishments.json")));
        return establishments;
    }

    protected List<Establishments> getEstablishments() throws IOException {
        JsonUtils jsonUtils = new JsonUtils();
        return jsonUtils.getEstablishments(getEstablishmentsJsonFromFile());
    }


    protected Map<String, String> getScoreDescriptors() throws IOException {
        JsonUtils jsonUtils = new JsonUtils();
        return jsonUtils.getRatings(getScoreDescriptorsJsonFromFile());
    }
}
