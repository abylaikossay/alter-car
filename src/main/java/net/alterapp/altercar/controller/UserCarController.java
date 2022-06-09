package net.alterapp.altercar.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import net.alterapp.altercar.model.requests.UserCarRequest;
import net.alterapp.altercar.service.UserCarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user-car")
public class UserCarController extends BaseController {

  private final UserCarService userCarService;

  @PostMapping("/add")
  public ResponseEntity<?> addUserCar(@RequestBody UserCarRequest userCarRequest) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return buildResponse(userCarService.addCar(userCarRequest, ((User) principal).getUsername()), HttpStatus.OK);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateUserCar(@RequestBody UserCarRequest userCarRequest,
                                         @PathVariable Long id) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return buildResponse(userCarService.updateUserCar(id, userCarRequest, ((User) principal).getUsername()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserCarById(@PathVariable Long id) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return buildResponse(userCarService.getUserCarById(id, ((User) principal).getUsername()), HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<?> getAllUserCars() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return buildResponse(userCarService.getAllUserCars(((User) principal).getUsername()), HttpStatus.OK);
  }

  @PostMapping("/gift/{id}")
  public ResponseEntity<?> giftCar(@RequestParam String userPhone, @PathVariable Long carId) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    userCarService.giftCar(((User) principal).getUsername(), userPhone, carId);
    return buildSuccessResponse("Машина успешно передана");
  }

}
