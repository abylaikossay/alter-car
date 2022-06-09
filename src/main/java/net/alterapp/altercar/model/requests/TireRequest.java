package net.alterapp.altercar.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.enums.TireSeasonEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TireRequest {
  private String name;
  private MultipartFile photo;
  private Integer diameter;
  @Enumerated(value = EnumType.STRING)
  private TireSeasonEnum season;
  private Integer width;
  private Integer height;
  private Double price;
  private Long tireBrandId;
  private String quality;
  private Integer amount;
}
