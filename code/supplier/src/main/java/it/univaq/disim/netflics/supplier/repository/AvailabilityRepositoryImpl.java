package it.univaq.disim.netflics.supplier.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class AvailabilityRepositoryImpl implements AvailabilityRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(AvailabilityRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;


}
