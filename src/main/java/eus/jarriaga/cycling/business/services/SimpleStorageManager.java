package eus.jarriaga.cycling.business.services;

import eus.jarriaga.cycling.business.exceptions.StorageException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Component
public class SimpleStorageManager implements StorageManager {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private Environment env;

    private Path rootLocation;

    private String directory = "";

    @Override
    public void init() throws StorageException {
        try {
            rootLocation = Paths.get(env.getProperty("resources.files.location"));
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void init(String type) throws StorageException {
        switch(type) {
            case "pattern":
                directory = "patterns/";
                break;
            case "object3d":
                directory = "object3ds/";
                break;
            case "image":
                directory = "images/";
                break;
            case "video":
                directory = "videos/";
                break;
            default:
                directory = "";
        }
        try {
            rootLocation = Paths.get(env.getProperty("resources.files.location") + directory);
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) throws StorageException{
        init();
        return storeFile(file);
    }

    @Override
    public String store(MultipartFile file, String type) throws StorageException {
        init(type);
        return storeFile(file);
    }

    private String storeFile(MultipartFile file) throws StorageException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        return "/files/" + directory + file.getOriginalFilename();
    }

    @Override
    public Stream<Path> loadAll() throws StorageException {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) throws StorageException {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
