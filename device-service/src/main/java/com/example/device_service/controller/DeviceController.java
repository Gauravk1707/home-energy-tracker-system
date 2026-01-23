package com.example.device_service.controller;

import com.example.device_service.dto.DeviceDto;
import com.example.device_service.entity.Device;
import com.example.device_service.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id){
        DeviceDto dto = deviceService.getDeviceById(id);
        if(dto==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto,HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto deviceDto){
        DeviceDto dto = deviceService.createDevice(deviceDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable Long id, @RequestBody DeviceDto deviceDto){
        try{
            deviceService.updateDevice(id,deviceDto);
            return new ResponseEntity<>("Device updated successfully",HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>("No device Found",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id){
        deviceService.deleteDevice(id);
        return  ResponseEntity.noContent().build();
    }
}
