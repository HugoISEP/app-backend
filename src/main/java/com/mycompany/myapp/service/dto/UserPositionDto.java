package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.service.view.UserPositionView;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserPositionDto implements UserPositionView {
    Long id;

    @Size(max = 2048)
    String comment;

    Long remuneration;
    UserDTO user;
    PositionDTO position;
}
