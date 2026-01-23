package com.example.device_service.service;

import com.example.device_service.dto.DeviceDto;
import com.example.device_service.entity.Device;
import com.example.device_service.exception.DeviceNotFoundException;
import com.example.device_service.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }


    public DeviceDto getDeviceById(Long id) {

        return deviceRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(()-> new DeviceNotFoundException("No Device Found"));
    }



    public DeviceDto createDevice(DeviceDto deviceDto) {

        Device device = Device.builder()
                .name(deviceDto.getName())
                .type(deviceDto.getType())
                .location(deviceDto.getLocation())
                .userId(deviceDto.getUserId())
                .build();

        Device savedDevice = deviceRepository.save(device);

        return mapToDto(savedDevice);
    }

    private DeviceDto mapToDto(Device device){

        return DeviceDto.builder()
                .id(device.getId())
                .name(device.getName())
                .type(device.getType())
                .location(device.getLocation())
                .userId(device.getUserId())
                .build();
    }

    public void updateDevice(Long id, DeviceDto deviceDto) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(()-> new DeviceNotFoundException("No device Found"));

        device.setName(deviceDto.getName());
        device.setType(deviceDto.getType());
        device.setLocation(deviceDto.getLocation());
        device.setUserId(deviceDto.getUserId());

        deviceRepository.save(device);
    }

    public void deleteDevice(Long id) {

        Device device = deviceRepository.findById(id)
                .orElseThrow(()-> new DeviceNotFoundException("No device Found"));

        deviceRepository.delete(device);
    }
}
