package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.ClientInfoDto;
import com.example.backendsmartcities.entity.ClientInfo;
import com.example.backendsmartcities.repository.ClientInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
/**
 * Author: Badreddine TIRGANI
 */
@Service
public class ClientInfoService implements BaseService<ClientInfoDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Override
    public ClientInfoDto save(ClientInfoDto clientInfo) throws Exception {

        if (clientInfoRepository.existsById(clientInfo.getId())) {
            throw new Exception("this clientInfoname is already in use");
        }

        ClientInfo clientInfoSaved = clientInfoRepository.save(modelMapper.map(clientInfo, ClientInfo.class));
        return modelMapper.map(clientInfoSaved, ClientInfoDto.class);
    }

    @Override
    public ClientInfoDto update(long id, ClientInfoDto clientInfoRequest) throws Exception {
        ClientInfo clientInfo = clientInfoRepository.findById(id).orElseThrow(() -> new Exception("No clientInfo registered with id " + id));
        if (clientInfoRequest != null) {
            BeanUtils.copyProperties(clientInfoRequest, clientInfo, Stream.of(new BeanWrapperImpl(clientInfoRequest).getPropertyDescriptors()).map(FeatureDescriptor::getName).filter(propertyName -> new BeanWrapperImpl(clientInfoRequest).getPropertyValue(propertyName) == null).toArray(String[]::new));

        }
        ClientInfo u1 = clientInfoRepository.save(clientInfo);
        return modelMapper.map(u1, ClientInfoDto.class);
    }

    @Override
    public void softDelete(Long id) {

    }

    @Override
    public Optional<ClientInfoDto> findById(Long id) {
        Optional<ClientInfo> sc = clientInfoRepository.findById(id);
        return sc.map(clientInfo -> modelMapper.map(clientInfo, ClientInfoDto.class));
    }

    @Override
    public List<ClientInfoDto> getAll() {

        List<ClientInfo> clientInfos = clientInfoRepository.findAll(); // Assuming clientInfoRepository is an interface extending JpaRepository<clientInfo, Long>
        List<ClientInfoDto> clientInfoDos = new ArrayList<>();
        for(ClientInfo clientInfo:clientInfos){
            ClientInfoDto ClientInfoDto1 = modelMapper.map(clientInfo,ClientInfoDto.class);
            clientInfoDos.add(ClientInfoDto1);
        }
        return clientInfoDos;
    }

}
