package net.alterapp.altercar.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerRequest {
  private String name;
  private MultipartFile logo;
  private String link;
  private String email;
  private String phone;
  private String address;
  private Boolean visible = true;
  private String description;
  private String qrPath;
  private String pushText;
}
