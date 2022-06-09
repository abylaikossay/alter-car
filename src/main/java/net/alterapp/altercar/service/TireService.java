package net.alterapp.altercar.service;


import net.alterapp.altercar.model.entity.TireEntity;
import net.alterapp.altercar.model.requests.TireRequest;
import net.alterapp.altercar.model.responses.paginator.PageResponse;

import java.util.List;
import java.util.Optional;

public interface TireService {


    TireEntity create(TireRequest tireRequest, String userName);

    TireEntity getById(Long id);

    TireEntity update(TireRequest tireRequest, Long id, String userName);

    void delete(Long id, String userName);

    List<TireEntity> getAll(Optional<String> search, Optional<Integer> page,
                            Optional<Integer> size,
                            Optional<String[]> sortBy,
                            String userName);

    TireEntity save(TireEntity tireEntity);
}
