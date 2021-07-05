package io.github.arkanjoms.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.arkanjoms.spring.exampleapp.ExampleApp;
import io.github.arkanjoms.spring.exampleapp.dto.ExampleRequest;
import io.github.arkanjoms.spring.exampleapp.dto.ExampleResponse;
import io.github.arkanjoms.spring.test.context.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ExampleApp.class})
@AutoConfigureMockMvc
class IntegrationLibTest extends IntegrationTestBase {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql/clear-data.sql",
            })
    void shouldGetExampleById_notFound() throws Exception {
        mockMvc.perform(get("/api/examples/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql/clear-data.sql",
                    "classpath:/sql/insert-data.sql",
            })
    void shouldGetExampleById_ok() throws Exception {
        var response = mockMvc.perform(get("/api/examples/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
        var result = objectMapper.readValue(response.getContentAsString(), ExampleResponse.class);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("example");
    }

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql/clear-data.sql",
                    "classpath:/sql/insert-data.sql",
            })
    void shouldAddNewExample_ok() throws Exception {
        var requestDto = ExampleRequest.builder().name("new example").build();
        var response = mockMvc.perform(post("/api/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse();
        var result = objectMapper.readValue(response.getContentAsString(), ExampleResponse.class);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("new example");
    }

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql/clear-data.sql",
                    "classpath:/sql/insert-data.sql",
            })
    void shouldEditExample_ok() throws Exception {
        var requestDto = ExampleRequest.builder().id(1L).name("edited example").build();
        var response = mockMvc.perform(put("/api/examples/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
        var result = objectMapper.readValue(response.getContentAsString(), ExampleResponse.class);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("edited example");
    }

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql/clear-data.sql",
                    "classpath:/sql/insert-data.sql",
            })
    void shouldEditExample_nok_id_not_equals() throws Exception {
        var requestDto = ExampleRequest.builder().id(2L).name("edited example").build();
        var response = mockMvc.perform(put("/api/examples/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn().getResponse().getErrorMessage();
        assertThat(response).isNotNull();
    }

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql/clear-data.sql",
                    "classpath:/sql/insert-data.sql",
            })
    void shouldDeleteExample_ok() throws Exception {
        mockMvc.perform(delete("/api/examples/1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        mockMvc.perform(get("/api/examples/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
