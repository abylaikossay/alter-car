package net.alterapp.altercar.model.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.entity.TireBrandEntity;
import net.alterapp.altercar.model.enums.TireSeasonEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TireResponse {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
    private Integer diameter;

    @Enumerated(value = EnumType.STRING)
    private TireSeasonEnum season;

    private Integer width;
    private Integer height;
    private Double price;
    private String name;
    private TireBrandEntity brand;
    private String quality;
    private Integer amount;
    private String photoUrl;
}
