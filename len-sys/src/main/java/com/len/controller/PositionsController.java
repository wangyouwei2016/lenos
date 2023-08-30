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

    @GetMapping("/{id}")
    public Result<SysPositions> get(@PathVariable String id) {
        return Result.ok(
                positionsService.get(id)
        );
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

    @PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Result<SysPositions> add(@RequestBody @Valid SysPositions position) {
        positionsService.add(position);
        return Result.ok(position);
    }

    @PostMapping(value = "")
    public Result<SysPositions> add1(@ModelAttribute @Valid SysPositions position) {
        positionsService.add(position);
        return Result.ok(position);
    }

    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Result<SysPositions> add2(@ModelAttribute @Valid SysPositions position) {
        positionsService.add(position);
        return Result.ok(position);
    }

    @PostMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Result<SysPositions> update(
        @PathVariable String id,
        @RequestBody @Valid SysPositions position) {
        position.setId(id);
        positionsService.update(position);
        return Result.ok(position);
    }

    @PostMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Result<SysPositions> update1(
        @PathVariable String id,
        @ModelAttribute @Valid SysPositions position) {
        position.setId(id);
        positionsService.update(position);
        return Result.ok(position);
    }

    @PostMapping(value = "/{id}")
    public Result<SysPositions> update2(
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

    @PostMapping("/delete")
    public Result<Void> deleteById(@RequestParam String id) {
        positionsService.delete(id);
        return Result.ok();
    }
}
