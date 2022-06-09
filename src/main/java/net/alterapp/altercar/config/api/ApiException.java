package net.alterapp.altercar.config.api;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiException extends RuntimeException {

  private List<ApiSubError> subErrors;

}
