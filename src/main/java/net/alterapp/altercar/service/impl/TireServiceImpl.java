package net.alterapp.altercar.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.methods.Pagination;
import net.alterapp.altercar.model.entity.*;
import net.alterapp.altercar.model.requests.TireRequest;
import net.alterapp.altercar.model.responses.errors.ErrorCode;
import net.alterapp.altercar.model.responses.errors.ServiceException;
import net.alterapp.altercar.predicates.TirePredicateBuilder;
import net.alterapp.altercar.repository.TireRepository;
import net.alterapp.altercar.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class TireServiceImpl implements TireService {

    private final TireRepository tireRepository;
    private final FileService fileService;
    private final TireBrandService tireBrandService;
    private final AccountService accountService;
    private final UserInfoService userInfoService;
    private final Pagination pagination;
    private final PartnerService partnerService;



    @Override
    public TireEntity create(TireRequest tireRequest, String userName) {
        AccountEntity accountEntity = accountService.getByUsername(userName);
        UserInfoEntity userInfoEntity = userInfoService.getByUserId(accountEntity.getId());
        PartnerEntity partnerEntity = partnerService.getById(userInfoEntity.getPartner().getId());
        checkIfExists(tireRequest.getName());
        TireEntity tireEntity = new TireEntity();
        TireBrandEntity tireBrandEntity = tireBrandService.getById(tireRequest.getTireBrandId());
        if (tireRequest.getLogo() != null) {
            String file = fileService.storeFile(tireRequest.getLogo(), "tire-photo");
            tireEntity.setPhotoUrl(file);
        }
        tireEntity.setName(tireRequest.getName());
        tireEntity.setAmount(tireRequest.getAmount());
        tireEntity.setBrand(tireBrandEntity);
        tireEntity.setDiameter(tireRequest.getDiameter());
        tireEntity.setHeight(tireRequest.getHeight());
        tireEntity.setPrice(tireRequest.getPrice());
        tireEntity.setQuality(tireRequest.getQuality());
        tireEntity.setSeason(tireRequest.getSeason());
        tireEntity.setPartner(partnerEntity);
        tireEntity.setWidth(tireRequest.getWidth());
        tireRepository.save(tireEntity);
        return tireEntity;
    }

    @Override
    public TireEntity getById(Long id) {
        return tireRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Tire Brand Found! " + id));
    }

    public void checkIfTireBelongs(String userName, TireEntity tireEntity) {
        AccountEntity accountEntity = accountService.getByUsername(userName);
        UserInfoEntity userInfoEntity = userInfoService.getByUserId(accountEntity.getId());
        if (!userInfoEntity.getPartner().getId().equals(tireEntity.getPartner().getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("Ошибка! Вы не имеете доступ к данному партнеру")
                    .build();
        }
    }

    @Override
    public TireEntity update(TireRequest tireRequest, Long id, String userName) {
        TireEntity tireEntity = getById(id);
        TireBrandEntity tireBrandEntity = tireBrandService.getById(tireRequest.getTireBrandId());
        checkIfTireBelongs(userName, tireEntity);
        if (tireRequest.getLogo() != null) {
            if (tireEntity.getPhotoUrl() != null) {
                fileService.delete(tireEntity.getPhotoUrl(), "tire-photo");
            }
            String photo_url = fileService.storeFile(tireRequest.getLogo(), "tire-photo");
            tireEntity.setPhotoUrl(photo_url);
        }
        tireEntity.setName(tireRequest.getName());
        tireEntity.setAmount(tireRequest.getAmount());
        tireEntity.setBrand(tireBrandEntity);
        tireEntity.setDiameter(tireRequest.getDiameter());
        tireEntity.setHeight(tireRequest.getHeight());
        tireEntity.setPrice(tireRequest.getPrice());
        tireEntity.setQuality(tireRequest.getQuality());
        tireEntity.setSeason(tireRequest.getSeason());
        tireEntity.setWidth(tireRequest.getWidth());
        tireRepository.save(tireEntity);
        return tireEntity;
    }

    @Override
    public void delete(Long id, String userName) {
        TireEntity tireEntity = getById(id);
        checkIfTireBelongs(userName, tireEntity);
        if (tireEntity.getPhotoUrl() != null) {
            fileService.delete(tireEntity.getPhotoUrl(), "tire-photo");
        }
        tireEntity.setDeletedAt(new Date());
        tireRepository.save(tireEntity);
    }

    @Override
    public List<TireEntity> getAll(Optional<String> search, Optional<Integer> page, Optional<Integer> size, Optional<String[]> sortBy, String userName) {
        TirePredicateBuilder builder = new TirePredicateBuilder();
        if (search.isPresent()) {
            Pattern pattern = Pattern.compile("(\\w+?[.]\\w+?|\\w+?)" +
                    "(:|<|>|!:)" +
                    "(\\w+?\\s\\w+?|\\w+?|\\w+?\\s\\w+?\\s\\w+?|\\w+?[-]\\w+?[-]\\w+?\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search.get() + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        AccountEntity accountEntity = accountService.getByUsername(userName);
        UserInfoEntity userInfoEntity = userInfoService.getByUserId(accountEntity.getId());
        PartnerEntity partnerEntity = partnerService.getById(userInfoEntity.getPartner().getId());
        BooleanExpression exp = builder.build();
//        Page<TireEntity> tiresPage = tireRepository.findAll(exp, pagination.paginate(page, size, sortBy));
//        return pagination.tireResponses(tiresPage);
        return tireRepository.findAllByDeletedAtIsNullAndPartner_Id(partnerEntity.getId());
    }

    private void checkIfExists(String name) {
        Optional<TireEntity> tireEntity = tireRepository.findByName(name);
        if (tireEntity.isPresent()) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .httpStatus(HttpStatus.CONFLICT)
                    .message("Ошибка! Шина с таким наименованием уже существует")
                    .build();
        }
    }
}
