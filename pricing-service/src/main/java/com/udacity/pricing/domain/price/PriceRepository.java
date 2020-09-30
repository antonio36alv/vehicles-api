package com.udacity.pricing.domain.price;

import org.springframework.data.repository.CrudRepository;
import com.udacity.pricing.domain.price.Price;

public interface PriceRepository extends CrudRepository<Price, Long> {

}
