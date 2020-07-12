package com.atu.user.query;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
}
