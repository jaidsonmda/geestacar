package dev.jaidson.geestacar.controller;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.CarDTO;
import dev.jaidson.geestacar.dto.RevenueDTO;
import dev.jaidson.geestacar.dto.SpotOutDTO;
import dev.jaidson.geestacar.enums.Sector;
import dev.jaidson.geestacar.filter.*;
import dev.jaidson.geestacar.filter.FilterRevenue;
import dev.jaidson.geestacar.filter.FilterSpotStatus;
import dev.jaidson.geestacar.mapper.CarMapper;
import dev.jaidson.geestacar.mapper.SpotMapper;
import dev.jaidson.geestacar.service.CarService;
import dev.jaidson.geestacar.service.EventService;
import dev.jaidson.geestacar.service.SpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.Objects;
import java.util.Optional;

import static dev.jaidson.geestacar.enums.Sector.A;
import static dev.jaidson.geestacar.enums.Sector.B;

@RestController
@RequestMapping("/")
@Tag(name = "Estacionamento", description = "Operações relacionadas ao status de vagas, placas e faturamento")
public class ParkingController {
    @Autowired
    private SpotService spotService;
    @Autowired
    private EventService eventService;
    @Autowired
    private CarService carService;

    @PostMapping("/plate-status")
    @Operation(
            summary = "Consultar status de uma placa",
            description = "Retorna as informações de um veículo com base na placa informada.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FilterPlateStatus.class),
                            examples = @ExampleObject(value = "{\"licensePlate\":\"ABC-1234\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Veículo encontrado", content = @Content(schema = @Schema(implementation = CarDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Placa não encontrada")
            }
    )
    public ResponseEntity<CarDTO> plateStatus(@RequestBody FilterPlateStatus plateStatus) {
        Optional<Car> car = carService.findByLicensePlate(plateStatus.getLicensePlate());
        if (car.isPresent()) {
            return ResponseEntity.ok(CarMapper.carDTO(car.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/spot-status")
    @Operation(
            summary = "Consultar status de uma vaga",
            description = "Retorna o status de uma vaga com base em coordenadas (lat, lng).",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FilterSpotStatus.class),
                            examples = @ExampleObject(value = "{\"lat\": -23.5505, \"lng\": -46.6333}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status da vaga encontrado", content = @Content(schema = @Schema(implementation = SpotOutDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
            }
    )
    public ResponseEntity<SpotOutDTO> spotStatus(@RequestBody FilterSpotStatus spotStatus){
        Optional<Spot> spot = spotService.findByLatAndLng(spotStatus.getLat(), spotStatus.getLng());
        if(spot.isPresent()) {


            return ResponseEntity.ok(SpotMapper.spotOutDTO(spot.get()));
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/revenue")
    @Operation(
            summary = "Consultar receita por setor",
            description = "Calcula a receita total de um setor (A ou B) em uma data específica.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FilterRevenue.class),
                            examples = @ExampleObject(value = "{\"sector\":\"A\", \"date\":\"2025-06-30\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Receita retornada com sucesso", content = @Content(schema = @Schema(implementation = RevenueDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Setor inválido ou inexistente")
            }
    )
    public ResponseEntity<RevenueDTO> revevenue(@RequestBody FilterRevenue filterRevenue){
        if(filterRevenue.getSector()== A || filterRevenue.getSector() == B) {
            Double priceUntilnow = 0.0;
            for (Spot spot : spotService.findAllBySector(filterRevenue.getSector(), filterRevenue.getDate())) {
                priceUntilnow = Objects.isNull(spot.getPriceUntilNow()) ? priceUntilnow + priceUntilnow : spot.getPriceUntilNow() + priceUntilnow;
            }

            return ResponseEntity.ok(SpotMapper.revenueDTO(priceUntilnow, "BRL", filterRevenue.getDate()));
        }
        return ResponseEntity.notFound().build();
    }
}
