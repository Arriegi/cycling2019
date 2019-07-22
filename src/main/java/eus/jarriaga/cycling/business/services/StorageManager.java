package eus.jarriaga.cycling.business.services;

import eus.jarriaga.cycling.business.exceptions.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageManager {

    void init() throws StorageException;

    void init(String type) throws StorageException;

    String store(MultipartFile file) throws StorageException;

    String store(MultipartFile file, String type) throws StorageException;

    Stream<Path> loadAll() throws StorageException;

    Path load(String filename) throws StorageException;

    Resource loadAsResource(String filename) throws StorageException;

    void deleteAll();

}
