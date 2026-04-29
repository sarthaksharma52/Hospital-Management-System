package cg.hospital.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ProcedureEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    // TEST 1 — GET /api/procedures → 200 OK + _embedded.procedures array
    // Spring Data REST wraps lists in HAL format: { "_embedded": { "procedures": [...] } }
    @Test
    void testGetAllProcedures() throws Exception {
        mockMvc.perform(get("/api/procedures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.procedures").isArray());
    }

    // TEST 2 — GET /api/procedures/1 → 200 OK + name and cost fields present
    // Code 1 = Reverse Rhinopodoplasty (exists in seed data)
    @Test
    void testGetProcedureById_found() throws Exception {
        mockMvc.perform(get("/api/procedures/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists())  // name field is in the response
                .andExpect(jsonPath("$.cost").exists()); // cost field is in the response
    }

    // TEST 3 — GET /api/procedures/99999 → 404 Not Found
    // Spring Data REST returns 404 automatically when ID is not in DB
    @Test
    void testGetProcedureById_notFound() throws Exception {
        mockMvc.perform(get("/api/procedures/99999"))
                .andExpect(status().isNotFound());
    }

    // TEST 4 — POST then GET to verify the record was actually saved
    // WHY TWO STEPS: Spring Data REST POST returns 201 with EMPTY body.
    //               We cannot assert $.name on the POST response — body is null.
    //               Solution: POST to save → GET the same ID → assert the data there.
    @Test
    void testCreateProcedure() throws Exception {
        String json = "{\"code\": 8001, \"name\": \"MockMVC Test Proc\", \"cost\": 750.0}";

        // Step 1: POST — only assert status 201, body will be empty (Spring Data REST default)
        mockMvc.perform(post("/api/procedures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        // Step 2: GET the created record — NOW assert name and cost are correct
        mockMvc.perform(get("/api/procedures/8001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MockMVC Test Proc"))
                .andExpect(jsonPath("$.cost").value(750.0));
    }

    // TEST 5 — POST to create, PUT to update, GET to verify the update was saved
    // WHY THREE STEPS: Spring Data REST PUT returns 204 NO CONTENT (empty body).
    //                  We cannot assert $.cost on the PUT response — body is null.
    //                  Solution: POST → PUT → GET and assert the new cost value.
    @Test
    void testUpdateProcedureCost() throws Exception {
        // Step 1: Create the record
        String createJson = "{\"code\": 8002, \"name\": \"Update Cost Test\", \"cost\": 100.0}";
        mockMvc.perform(post("/api/procedures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated());

        // Step 2: PUT to update cost — expect 204 No Content (empty body, that is normal)
        String updateJson = "{\"code\": 8002, \"name\": \"Update Cost Test\", \"cost\": 9999.0}";
        mockMvc.perform(put("/api/procedures/8002")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNoContent()); // 204 = saved OK, nothing returned

        // Step 3: GET the record — verify cost is now 9999
        mockMvc.perform(get("/api/procedures/8002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cost").value(9999.0));
    }
}