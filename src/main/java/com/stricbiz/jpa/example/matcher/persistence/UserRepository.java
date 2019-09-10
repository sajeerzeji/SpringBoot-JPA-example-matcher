package com.stricbiz.jpa.example.matcher.persistence;

import com.stricbiz.jpa.example.matcher.persistence.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer>,
        QueryByExampleExecutor<UserEntity>, JpaSpecificationExecutor<UserEntity> {
    public default Specification<UserEntity> getFromDateRangeAndExample(
            String fromDate, String toDate, Example<UserEntity> example) {
        final Date from = !StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)
                ? new Date(Long.valueOf(fromDate) * 1000L) : null;
        final Date to = !StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)
                ? new Date(Long.valueOf(toDate) * 1000L) : null;

        return (Specification<UserEntity>) (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if(from != null) {
                predicates.add(builder.greaterThan(root.get("dateJoined"), from));
            }

            if(to != null) {
                predicates.add(builder.lessThan(root.get("dateJoined"), to));
            }

            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
