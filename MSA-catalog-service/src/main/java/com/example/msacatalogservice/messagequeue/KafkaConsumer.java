package com.example.msacatalogservice.messagequeue;

import com.example.msacatalogservice.entity.CatalogEntity;
import com.example.msacatalogservice.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class KafkaConsumer {

    private CatalogRepository catalogRepository;

    public KafkaConsumer(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Transactional
    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka Message: -> " + kafkaMessage);

        // String으로 들어온 message를 map으로 변환하여 사용
        Map<Object, Object> map = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();

        try{
            // String으로 들어온 json형식을 map으로 바꿔준다.
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        CatalogEntity entity = null;
        try {
            entity = catalogRepository.findByProductId((String) map.get("productId"))
                    .orElseThrow(() -> new Exception("없는 상품번호입니다."));
        } catch (Exception e) {
            e.printStackTrace();
        }

        entity.setStock(entity.getStock() - (Integer) map.get("qty"));

        System.out.println("catalog entity : " + entity);

    }
}
