package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.ImageDto;
import com.example.backendsmartcities.entity.Image;
import com.example.backendsmartcities.repository.ImageRepository;
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
public class ImageService implements BaseService<ImageDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageRepository imageRepository;


    @Override
    public ImageDto save(ImageDto image) throws Exception {
      
        if (imageRepository.existsById(image.getId())) {
            throw new Exception("this imagename is already in use");
        }

        Image imageSaved = imageRepository.save(modelMapper.map(image, Image.class));
        return modelMapper.map(imageSaved, ImageDto.class);
    }

    @Override
    public ImageDto update(long id, ImageDto imageRequest) throws Exception {
        Image image = imageRepository.findById(id).orElseThrow(() -> new Exception("No image registered with id " + id));
        if (imageRequest != null) {
            BeanUtils.copyProperties(imageRequest, image, Stream.of(new BeanWrapperImpl(imageRequest).getPropertyDescriptors()).map(FeatureDescriptor::getName).filter(propertyName -> new BeanWrapperImpl(imageRequest).getPropertyValue(propertyName) == null).toArray(String[]::new));

        }
        Image u1 = imageRepository.save(image);
        return modelMapper.map(u1, ImageDto.class);
    }

    @Override
    public void softDelete(Long id) {

        //TODO delete
    }

    @Override
    public Optional<ImageDto> findById(Long id) {
        Optional<Image> sc = imageRepository.findById(id);
        return sc.map(image -> modelMapper.map(image, ImageDto.class));
    }

    @Override
    public List<ImageDto> getAll() {

        List<Image> images = imageRepository.findAll(); // Assuming imageRepository is an interface extending JpaRepository<image, Long>
        List<ImageDto> imageDos = new ArrayList<>();
        for(Image image:images){
            ImageDto ImageDto1 = modelMapper.map(image,ImageDto.class);
            imageDos.add(ImageDto1);
        }
        return imageDos;
    }

}
