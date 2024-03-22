package jmaster.io.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jmaster.io.demo.entity.BillItem;

public interface BillItemRepo extends JpaRepository<BillItem, Integer>{

}
