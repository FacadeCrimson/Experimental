package com.github.kafka;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Unit test for simple App.
 */
class BankTransactionsProducerTest {
    /**
     * Rigorous Test.
     * 
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @Test
    void testApp() throws JsonMappingException, JsonProcessingException {
        ProducerRecord<String,String>record = BankTransactionsProducer.newRandomTransaction("john");
        String key = record.key();
        String value = record.value();

        assertEquals(key, "john");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(value);
        assertEquals(node.get("name").asText(), "john");
        assertTrue(node.get("amount").asInt()<100,"Amount should be less than 100.");
        assertEquals(node.get("time").asText(), "john");
    }
}
