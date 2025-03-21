package com.JavaLab.AdvJava.models;

import org.springframework.data.repository.CrudRepository;

//An interface for interacting with our DB and goods objects
public interface RztkGoodsRepository extends CrudRepository<RztkGood,Long> {
}
