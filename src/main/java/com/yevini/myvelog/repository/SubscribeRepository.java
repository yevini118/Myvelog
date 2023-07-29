package com.yevini.myvelog.repository;

import com.yevini.myvelog.model.monbodb.Subscribe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubscribeRepository extends MongoRepository<Subscribe, String> {

    List<Subscribe> findBySubscriber(String subscriber);
}
