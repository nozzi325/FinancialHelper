package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
