package net.alterapp.altercar.methods;


import lombok.AllArgsConstructor;
import net.alterapp.altercar.model.entity.TireEntity;
import net.alterapp.altercar.model.responses.TireResponse;
import net.alterapp.altercar.model.responses.paginator.PageResponse;
import net.alterapp.altercar.model.responses.paginator.PageableResponse;
import net.alterapp.altercar.model.responses.paginator.SortResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Pagination {

    private static ModelMapper modelMapper = new ModelMapper();


    public PageRequest paginate(Optional<Integer> page,
                                Optional<Integer> size,
                                Optional<String[]> sortBy) {
        Sort sort = Sort.by("id");
        if (sortBy.isPresent()) {
            String[] sorters = sortBy.get();
            List<Sort.Order> sorts = Arrays.stream(sorters)
                    .map(s -> s.split("-")[0].trim().equalsIgnoreCase("asc")
                            ? Sort.Order.asc(s.split("-")[1]) : Sort.Order.desc(s.split("-")[1]))
                    .collect(Collectors.toList());
            sort = Sort.by(sorts);
        }
        return PageRequest.of(page.orElse(0), size.orElse(5), sort);
    }

    public Integer calculateUserAge(Date dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateofBirth = dateOfBirth.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        Period diff = Period.between(dateofBirth, currentDate);
        return diff.getYears();
    }

    public PageResponse tireResponses(Page<TireEntity> tireList) {
        Type listType = new TypeToken<List<TireResponse>>() {
        }.getType();
        List<TireResponse> tireResponses = modelMapper.map(tireList.getContent(), listType);
        List<Long> userIds = new ArrayList<>();

        return pageResponse(tireList, tireResponses);
    }

    public PageResponse pageResponse(Page<?> page, List<?> responses) {
        SortResponse sortResponse = SortResponse.builder()
                .sorted(page.getSort().isSorted())
                .unsorted(page.getSort().isUnsorted())
                .empty(page.getSort().isEmpty())
                .build();

        PageableResponse pageableResponse = PageableResponse.builder()
                .sortResponse(sortResponse)
                .pageNumber(page.getPageable().getPageNumber())
                .pageSize(page.getPageable().getPageSize())
                .paged(page.getPageable().isPaged())
                .build();

        PageResponse pageResponse =
                PageResponse.builder()
                        .totalPages(page.getTotalPages())
                        .content(responses)
                        .last(page.isLast())
                        .first(page.isFirst())
                        .size(page.getSize())
                        .totalElements(page.getTotalElements())
                        .pageable(pageableResponse)
                        .numberOfElements(page.getNumberOfElements())
                        .build();
        return pageResponse;
    }

}
