package com.exercise.inventorymanager.item;

import com.exercise.inventorymanager.item.controller.ErrorMessage;
import com.exercise.inventorymanager.item.model.Item;
import com.exercise.inventorymanager.item.repository.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MongoClient mongoClient;

    @MockBean
    private ItemRepository itemRepository;

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
        when(itemRepository.findAll()).thenReturn(List.of(createItem()));

        this.mockMvc.perform(get("/item"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(List.of(createItem()))));
    }

    @Test
    public void postShouldReturnBadRequestWhenInputIsInsufficient() throws Exception {
        Item invalidItem = new Item();

        this.mockMvc.perform(
                        post("/item")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapToJson(invalidItem))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createErrorMessageJson("invalid input"))
                );
    }

    @Test
    public void postShouldReturnCreatedWhenInputIsValid() throws Exception {
        Item newItem = createItem();
        when(itemRepository.save(newItem)).thenReturn(newItem);

        this.mockMvc.perform(
                        post("/item")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapToJson(newItem))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(newItem)));
    }

    @Test
    public void getShouldReturnItemWhenIDExists() throws Exception {
        Item existent = createItem();
        when(itemRepository.findById(1234)).thenReturn(Optional.of(existent));

        this.mockMvc.perform(get("/item/" + existent.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(existent))
                );
    }

    @Test
    public void getShouldReturnBadRequestWhenIDIsInvalid() throws Exception {
        this.mockMvc.perform(get("/item/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createErrorMessageJson("invalid input"))
                );
    }

    @Test
    public void getShouldReturnNotFoundWhenItemNotFound() throws Exception {
        this.mockMvc.perform(get("/item/1111"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createErrorMessageJson("item not found"))
                );
    }

    @Test
    public void putShouldReturnOkWhenInputIsValid() throws Exception {
        Item existent = createItem();
        Item toUpdate = createUpdatedItem();

        when(itemRepository.findById(existent.getId())).thenReturn(Optional.of(existent));
        when(itemRepository.save(toUpdate)).thenReturn(toUpdate);

        this.mockMvc.perform(
                        put("/item/" + toUpdate.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapToJson(toUpdate))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapToJson(toUpdate)));
    }

    @Test
    public void putShouldReturnBadRequestWhenIDIsInvalid() throws Exception {
        this.mockMvc.perform(put("/item/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createErrorMessageJson("invalid input"))
                );
    }

    @Test
    public void putShouldReturnNotFoundWhenItemNotFound() throws Exception {
        Item updated = createUpdatedItem();

        this.mockMvc.perform(
                        put("/item/" + updated.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapToJson(updated))
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createErrorMessageJson("item not found"))
                );
    }

    @Test
    public void deleteShouldReturnBadRequestWhenIDIsInvalid() throws Exception {
        this.mockMvc.perform(delete("/item/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createErrorMessageJson("invalid input"))
                );
    }

    @Test
    public void deleteShouldReturnNotFoundWhenItemNotFound() throws Exception {
        this.mockMvc.perform(delete("/item/1111"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createErrorMessageJson("item not found"))
                );
    }

    @Test
    public void deleteShouldReturnNotFoundWhenOkWhenInputIsValid() throws Exception {
        Item existent = createItem();
        when(itemRepository.findById(existent.getId())).thenReturn(Optional.of(existent));

        this.mockMvc.perform(delete("/item/" + existent.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private Item createItem() {
        return new Item(1234, "hammer", 12, 12.45);
    }

    private Item createUpdatedItem() {
        return new Item(1234, "hammer", 25, 23.68);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

    private String createErrorMessageJson(String message) throws JsonProcessingException {
        ErrorMessage errorMessage = new ErrorMessage(message);
        return mapToJson(errorMessage);
    }
}