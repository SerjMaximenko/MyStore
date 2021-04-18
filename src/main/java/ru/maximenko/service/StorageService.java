package ru.maximenko.service;

import lombok.extern.slf4j.Slf4j;
import ru.maximenko.dao.StorageDao;
import ru.maximenko.entity.Storage;

@Slf4j
public class StorageService {

    private final StorageDao storageDao = new StorageDao();

    public String addStorage(Storage storage){
        log.info(storage.getName());
        if (storageDao.findByName(storage.getName()) != null) return "Склад уже существует";
        storageDao.addStorage(storage);
        return "Успешно";
    }

}
