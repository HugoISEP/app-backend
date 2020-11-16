package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PositionRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.service.MissionService;
import com.mycompany.myapp.service.PositionService;
import com.mycompany.myapp.service.dto.PositionDTO;
import com.mycompany.myapp.service.mapper.MissionMapper;
import com.mycompany.myapp.service.mapper.PositionMapper;
import com.mycompany.myapp.service.view.MissionView;
import com.mycompany.myapp.service.view.PositionView;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/position")
@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.MANAGER + "\") || hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
public class PositionController {

    private static final String ENTITY_NAME = "position";

    private final PositionRepository repository;
    private final PositionService service;
    private final PositionMapper mapper;
    private final MissionService missionService;
    private final MissionMapper missionMapper;

    public PositionController(PositionRepository repository, PositionService service, PositionMapper mapper, MissionService missionService, MissionMapper missionMapper) {
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
        this.missionService = missionService;
        this.missionMapper = missionMapper;
    }

    @GetMapping("/{id}")
    public PositionView getById(@PathVariable Long id){
        service.hasAuthorization(id);
        return mapper.asDto(repository.findById(id).orElseThrow(() -> new BadRequestAlertException("position doesn't exist", ENTITY_NAME, "id doesn't exist")));
    }

    @GetMapping("/mission/{id}")
    public List<PositionView> getPositionsByMission(@PathVariable Long id){
        missionService.hasAuthorization(id);
        return repository.findAllByMissionId(id);
    }

    @GetMapping("/active")
    public List<PositionView> getActivePositionsByUser(){
        return service.getActivePositionsByUser();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<? extends PositionView> getAll(){
        return mapper.asListDTO(repository.findAll());
    }

    @PostMapping("/mission/{missionId}")
    public MissionView addPosition(@PathVariable Long missionId, @Valid @RequestBody PositionDTO position){
        missionService.hasAuthorization(missionId);
        return missionMapper.asDTO(service.addPosition(missionId, position));
    }

    @PutMapping()
    public PositionView edit(@Valid @RequestBody PositionDTO position){
        service.hasAuthorization(position.getId());
        return mapper.asDto(service.editPosition(position));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.hasAuthorization(id);
        service.deletePosition(id);
    }
}
