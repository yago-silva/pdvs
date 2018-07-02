package com.zxventures.challenge.controller;

import com.zxventures.challenge.controller.dto.PdvConverter;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.controller.dto.PdvDto;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.service.PdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(PdvController.PDVS_PATH)
public class PdvController {

    public final static String PDVS_PATH = "/pdvs";

    @Autowired
    private PdvService pdvService;

    @Autowired
    private PdvConverter pdvConverter;

    @PostMapping
    public ResponseEntity postPdv(@RequestBody @Valid PdvDto pdvDto) throws URISyntaxException {
        Pdv createdPdv = pdvService.save(pdvConverter.fromDtoToModel(pdvDto));
        return ResponseEntity.created(new URI(String.format("%s/%s",PDVS_PATH, createdPdv.getId()))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PdvDto> getPdv(@PathVariable("id") String id){
        return pdvService.findById(id)
                .map(pdv -> ResponseEntity.ok(pdvConverter.fromModelToDto(pdv)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/closer")
    public ResponseEntity<PdvDto> getCloserPdvBy(@RequestParam("lng") BigDecimal lng, @RequestParam("lat") BigDecimal lat){
        Point point = new Point(lng, lat);
        return pdvService.findCloserFrom(point).map(pdv -> ResponseEntity.ok(pdvConverter.fromModelToDto(pdv)))
                .orElse(ResponseEntity.notFound().build());
    }
}