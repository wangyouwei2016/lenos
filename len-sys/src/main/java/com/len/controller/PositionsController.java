package com.len.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.len.entity.SysPositions;
import com.len.response.Result;
import com.len.service.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/positions")
@Validated
public class PositionsController {
    private final PositionsService positionsService;

    @Autowired
    public PositionsController(PositionsService positionsService) {
        this.positionsService = positionsService;
    }

    @GetMapping
    public Result<IPage<SysPositions>> getAllByPage(
        @RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "10") Integer pageSize,
        SysPositions positions, OrderItem sort) {
        return Result.ok(
            positionsService.getAllPositionsByPage(new Page<>(pageNum, pageSize), positions, sort)
        );
    }

    @PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public Result<SysPositions> add(@ModelAttribute @Valid SysPositions position) {
        positionsService.add(position);
        return Result.ok(position);
    }

    @PostMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public Result<Object> update(
        @PathVariable String id,
        @ModelAttribute @Valid SysPositions position) {
        position.setId(id);
        positionsService.update(position);
        return Result.ok(position);
    }

    @PostMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable String id) {
        positionsService.delete(id);
        return Result.ok();
    }
}
