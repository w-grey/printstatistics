package com.wgrey.printstatistics.specification;

import com.wgrey.printstatistics.controller.Query;
import com.wgrey.printstatistics.model.Job;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;


public class JobSpecification {

    public static Specification<Job> getAllFiltered(Query query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.between(root.get("time"), query.getFrom(), query.getTo()));

            if (query.getUser() != null) {
                predicates.add(cb.equal(root.get("user"), query.getUser()));
            }
            if (query.getType() != null) {
                predicates.add(cb.equal(root.get("type"), query.getType()));
            }
            if (query.getDevice() != null) {
                predicates.add(cb.equal(root.get("device"), query.getDevice()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
