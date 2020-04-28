package com.voxhumana.urlrepository.controller;

import com.voxhumana.urlrepository.model.api.*;
import com.voxhumana.urlrepository.model.mongo.Mapping;
import com.voxhumana.urlrepository.repository.MongoMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RepositoryController {

    @Autowired
    private MongoMappingRepository mongoMappingRepository;

    @GetMapping("/mapping/source")
    public GetMappingResponse getMappingBySource(@RequestParam String url) {
        if (StringUtils.isEmpty(url))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL cannot be null or empty.");

        Optional<Mapping> foundMapping = mongoMappingRepository.findById(url);
        if (foundMapping.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find URL.");

        return new GetMappingResponse(foundMapping.get().getValue());
    }

    @GetMapping("/mapping/value")
    public GetMappingResponse getMappingByValue(@RequestParam String url) {
        if (StringUtils.isEmpty(url))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL cannot be null or empty.");

        Mapping foundMapping = mongoMappingRepository.findByValue(url);
        if (foundMapping == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find URL.");

        return new GetMappingResponse(foundMapping.getSource());
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            path = "/mapping"
    )
    public CreateMappingResponse createNewMapping(@Valid @RequestBody CreateMappingRequest request) {
        Mapping createMapping = new Mapping(request.getSource(), request.getValue(), request.getExpiresOn());
        Mapping savedMapping = mongoMappingRepository.insert(createMapping);

        return new CreateMappingResponse(savedMapping.getValue());
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            path = "/mapping"
    )
    public UpdateMappingResponse updateMapping(@Valid @RequestBody UpdateMappingRequest request) {
        Optional<Mapping> currentMapping = mongoMappingRepository.findById(request.getSource());
        if (currentMapping.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mapping cannot be found.");

        String updatedValue = StringUtils.isEmpty(request.getValue())
                ? currentMapping.get().getValue()
                : request.getValue();

        OffsetDateTime updatedExpiredOn = request.getExpiresOn() == null
                ? currentMapping.get().getExpiresOn()
                : request.getExpiresOn();

        Mapping updatedMapping = new Mapping(request.getSource(), updatedValue, updatedExpiredOn);
        mongoMappingRepository.save(updatedMapping);

        return new UpdateMappingResponse(updatedMapping.getValue());
    }

    @DeleteMapping("/mapping/source")
    public ResponseEntity<String> deleteMapping(@RequestParam String url) {
        if (StringUtils.isEmpty(url))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL cannot be null or empty.");

        mongoMappingRepository.deleteById(url);

        return ResponseEntity.ok("Delete successful");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
