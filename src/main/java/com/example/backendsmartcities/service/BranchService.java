package com.example.backendsmartcities.service;

import com.example.backendsmartcities.dto.BranchDto;
import com.example.backendsmartcities.entity.Branch;
import com.example.backendsmartcities.repository.BranchRepository;
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
public class BranchService implements BaseService<BranchDto>  {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public BranchDto save(BranchDto obj) throws Exception {
        if (branchRepository.existsById(obj.getId())) {
            throw new Exception("this Branch is already in use");
        }
        Branch branch = branchRepository.save(modelMapper.map(obj, Branch.class));
        return modelMapper.map(branch, BranchDto.class);
    }

    @Override
    public BranchDto update(long id, BranchDto obj) throws Exception {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new Exception("No branch registered with id " + id));
        if (branchRepository != null) {
            BeanUtils.copyProperties(obj, branch, Stream.of(new BeanWrapperImpl(obj).getPropertyDescriptors()).map(FeatureDescriptor::getName).filter(propertyName -> new BeanWrapperImpl(obj).getPropertyValue(propertyName) == null).toArray(String[]::new));

        }
        Branch u1 = branchRepository.save(branch);
        return modelMapper.map(u1, BranchDto.class);
    }

    @Override
    public void softDelete(Long id) {
        branchRepository.delete(id);
    }

    @Override
    public Optional<BranchDto> findById(Long id) {
        Optional<Branch> sc = branchRepository.findById(id);
        return sc.map(user -> modelMapper.map(user, BranchDto.class));
    }

    @Override
    public List<BranchDto> getAll() {
        List<Branch> all = branchRepository.findAll(); // Assuming UserRepository is an interface extending JpaRepository<User, Long>
        List<BranchDto> branchDtos = new ArrayList<>();
        for(Branch branch:all){

            branchDtos.add(modelMapper.map(branch,BranchDto.class));
        }
        return branchDtos;
    }
}
