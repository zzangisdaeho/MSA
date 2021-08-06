package com.example.msacatalogservice.service;

import com.example.msacatalogservice.entity.CatalogEntity;

public interface CatalogService {

    Iterable<CatalogEntity> getAllCatalogs();
}
