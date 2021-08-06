package com.example.msacatalogservice.controller;

import com.example.msacatalogservice.entity.CatalogEntity;
import com.example.msacatalogservice.service.CatalogService;
import com.example.msacatalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;
    private final ModelMapper mapper;

    public CatalogController(Environment env, CatalogService catalogService, @Qualifier("mapperStrict") ModelMapper mapper) {
        this.env = env;
        this.catalogService = catalogService;
        this.mapper = mapper;
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getUsers(){
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

        List<ResponseCatalog> result = new ArrayList<>();

        catalogList.forEach(catalog -> {
            result.add(mapper.map(catalog, ResponseCatalog.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
