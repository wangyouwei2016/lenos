package com.len.controller;

import com.len.entity.SysPositions;
import com.len.service.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionsController {
    private final PositionsService positionsService;

    @Autowired
    public PositionsController(PositionsService positionsService) {
        this.positionsService = positionsService;
    }

    @GetMapping
    public Page<SysPositions> getAllPositions(Pageable pageable) {
        return positionsService.findByPage(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SysPositions> getPositionById(@PathVariable String id) {
        SysPositions position = positionsService.getPositionById(id);
        return position != null ? ResponseEntity.ok(position) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SysPositions> createPosition(@RequestBody SysPositions position) {
        positionsService.createPosition(position);
        return ResponseEntity.status(HttpStatus.CREATED).body(position);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SysPositions> updatePosition(@PathVariable String id, @RequestBody SysPositions newPosition) {
        if (positionsService.getPositionById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        newPosition.setId(id);
        positionsService.updatePosition(newPosition);
        return ResponseEntity.ok(newPosition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable String id) {
        if (positionsService.getPositionById(id) != null) {
            positionsService.deletePosition(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
