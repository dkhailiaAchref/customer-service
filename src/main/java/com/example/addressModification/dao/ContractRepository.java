package com.example.addressModification.dao;

import com.example.addressModification.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//@Repository
@Service
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
