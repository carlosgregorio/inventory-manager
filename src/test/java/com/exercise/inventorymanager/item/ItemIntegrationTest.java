package com.exercise.inventorymanager.item;

import com.exercise.inventorymanager.item.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getShouldReturnAnEmptyListWhenNoDataFound() throws Exception {
        this.mockMvc.perform(get("/item"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]")
        );
    }

    @Test
    public void getShouldReturnAListWithItemsWhenDataIsFound() throws Exception {
        this.mockMvc.perform(get("/item"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\": 1234,\n" +
                        "    \"name\": \"hammer\",\n" +
                        "    \"quantity\": 12,\n" +
                        "    \"value\": 12.45\n" +
                        "  }\n" +
                        "]")
                );
    }

    @Test
    public void postShouldReturnBadRequestWhenInputIsInsufficient() throws Exception {
        Item item = new Item();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(item );

        this.mockMvc.perform(post("/item").content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("invalid input")
                );
    }

    @Test
    public void postShouldReturnCreatedWhenInputIsValid() throws Exception {
        Item item = new Item();
        item.setId(1234);
        item.setName("hammer");
        item.setQuantity(12);
        item.setValue(12.45);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(item );

        this.mockMvc.perform(post("/item").content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\": 1234,\n" +
                        "    \"name\": \"hammer\",\n" +
                        "    \"quantity\": 12,\n" +
                        "    \"value\": 12.45\n" +
                        "  }\n" +
                        "]")
                );
    }

    @Test
    public void getShouldReturnItemWhenIDExists() throws Exception {
        this.mockMvc.perform(get("/item/1234"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\": 1234,\n" +
                        "    \"name\": \"hammer\",\n" +
                        "    \"quantity\": 12,\n" +
                        "    \"value\": 12.45\n" +
                        "  }\n" +
                        "]")
                );
    }

    @Test
    public void getShouldReturnBadRequestWhenIDIsInvalid() throws Exception {
        this.mockMvc.perform(get("/item/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("invalid input")
                );
    }

    @Test
    public void getShouldReturnNotFoundWhenItemNotFound() throws Exception {
        this.mockMvc.perform(get("/item/1111"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("item not found")
                );
    }

    @Test
    public void putShouldReturnOkWhenInputIsValid() throws Exception {
        Item item = new Item();
        item.setId(1234);
        item.setName("hammer");
        item.setQuantity(25);
        item.setValue(23.68);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(item );

        this.mockMvc.perform(put("/item/1234").content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\": 1234,\n" +
                        "    \"name\": \"hammer\",\n" +
                        "    \"quantity\": 25,\n" +
                        "    \"value\": 23.68\n" +
                        "  }\n" +
                        "]")
                );
    }

    @Test
    public void putShouldReturnBadRequestWhenIDIsInvalid() throws Exception {
        this.mockMvc.perform(put("/item/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("invalid input")
                );
    }

    @Test
    public void putShouldReturnNotFoundWhenItemNotFound() throws Exception {
        this.mockMvc.perform(put("/item/1111"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("item not found")
                );
    }

    @Test
    public void deleteShouldReturnBadRequestWhenIDIsInvalid() throws Exception {
        this.mockMvc.perform(delete("/item/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("invalid input")
                );
    }

    @Test
    public void deleteShouldReturnNotFoundWhenItemNotFound() throws Exception {
        this.mockMvc.perform(delete("/item/1111"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("item not found")
                );
    }

    @Test
    public void deletetShouldReturnNotFoundWhenOkWhenInputIsValid() throws Exception {
        this.mockMvc.perform(delete("/item/1234"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}