package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.Event;
import dev.jaidson.geestacar.domain.RegisterEvent;
import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.EventDTO;
import dev.jaidson.geestacar.enums.EventType;
import dev.jaidson.geestacar.enums.Sector;
import dev.jaidson.geestacar.mapper.EventMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.jaidson.geestacar.enums.Sector.A;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {

    // Mocks para todas as dependências da ParkingService
    @Mock
    private SpotService spotService;
    @Mock
    private RegisterEventService registerEventService;
    @Mock
    private EventService eventService;
    @Mock
    private CarService carService;
    @Mock
    private GarageService garageService;

    // Injeta os mocks na instância da classe que estamos testando
    @InjectMocks
    private ParkingService parkingService;

    private Car car;

    private EventDTO eventEntryDTO = new EventDTO();
    private EventDTO eventParkedDTO = new EventDTO();
    private EventDTO eventExitDTO = new EventDTO();
    private RegisterEvent registerEvent;
    // Método de setup, executado antes de cada teste para inicializar objetos comuns
    @BeforeEach
    void setup() {
        car = new Car();
        car.setId(1L);
        car.setLicensePlate("ABC-1234");

       Event event= Event.builder().priceUntilNow(10.0).entryTime(LocalDate.now()).eventType(EventType.EXIT).build();


        eventEntryDTO.setLicensePlate("ABC-1234");
        eventEntryDTO.setEventType(EventType.ENTRY);
        eventEntryDTO.setEntryTime(LocalDate.now());


        eventParkedDTO.setLicensePlate("ABC-1234");
        eventParkedDTO.setEventType(EventType.PARKED);
        eventParkedDTO.setLat(-10.248456);
        eventParkedDTO.setLng(-48.322977);


        eventExitDTO.setLicensePlate("ABC-1234");
        eventExitDTO.setEventType(EventType.EXIT);
        eventExitDTO.setExitTime(LocalDate.now());

        registerEvent = RegisterEvent.builder().eventList(EventMapper.toDomainList(List.of(eventExitDTO,eventParkedDTO,eventEntryDTO))).build();


    }

    // --- Testes para o método entry() ---

    @Test
    @DisplayName("Deve permitir a entrada do carro quando há vagas disponíveis")
    void entry_WhenSpotsAvailable_ShouldAllowEntry() {
        // Given (Dado)
        given(spotService.findSpotUnoccupied()).willReturn(10); // Simula que há 10 vagas

        // When (Quando)
        ResponseEntity response = parkingService.entry(eventEntryDTO, car);

        // Then (Então)
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Evento recebido com sucesso.");

        // Verifica se o método save de EventService foi chamado, confirmando a criação do evento
        verify(eventService).save(any(Event.class));
        assertThat(car.getEntryTime()).isNotNull(); // Verifica se o tempo de entrada foi registrado no carro
    }

    @Test
    @DisplayName("Não deve permitir a entrada do carro quando não há vagas")
    void entry_WhenNoSpotsAvailable_ShouldDenyEntry() {
        // Given
        given(spotService.findSpotUnoccupied()).willReturn(0); // Simula que não há vagas

        // When
        ResponseEntity response = parkingService.entry(eventEntryDTO, car);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT); // Espera um status 409
        assertThat(car.isInTheGarage()).isFalse(); // Garante que o carro foi marcado como "fora da garagem"

        // Verifica se o método save de CarService foi chamado para atualizar o status do carro
        verify(carService).save(car);
        // Garante que nenhum evento de entrada foi criado
        verify(eventService, never()).save(any(Event.class));
    }

    // --- Testes para o método parked() ---

    @Test
    @DisplayName("Deve registrar o estacionamento do carro que já entrou")
    void parked_WhenCarHasEntryEvent_ShouldParkCar() {
        // Given
        //RegisterEvent registerEvent = RegisterEvent.builder().licensePlate(car.getLicensePlate()).eventList(new ArrayList<>()).build();
        registerEvent.setLicensePlate(car.getLicensePlate());
        Spot spot = new Spot();
        spot.setId(1L);

        given(registerEventService.findByLicensePlateAndExitTimeIsNull(anyString())).willReturn(Optional.of(registerEvent));
        given(spotService.findByLatAndLng(eventParkedDTO.getLat(), eventParkedDTO.getLng())).willReturn(Optional.of(spot));
        given(spotService.save(any(Spot.class))).willReturn(spot);
       // given(parkingService.parked(eventParkedDTO,car)).willReturn(ResponseEntity.ok("Sucesso"));
        // When
        ResponseEntity response = parkingService.parked(eventParkedDTO, car);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(eventService).save(any(Event.class)); // Verifica se o evento de "parked" foi salvo
        verify(spotService).save(spot); // Verifica se a vaga foi atualizada e salva

        assertThat(spot.isOccupied()).isTrue(); // Confirma que a vaga está ocupada
        assertThat(car.getLat()).isEqualTo(eventParkedDTO.getLat()); // Confirma a atualização da localização do carro
        assertThat(car.getLng()).isEqualTo(eventParkedDTO.getLng());
    }

    @Test
    @DisplayName("Não deve estacionar carro que não possui evento de entrada")
    void parked_WhenCarHasNoEntryEvent_ShouldReturnConflict() {
        // Given
        given(registerEventService.findByLicensePlateAndExitTimeIsNull(car.getLicensePlate())).willReturn(Optional.empty());

        // When
        ResponseEntity response = parkingService.parked(eventParkedDTO, car);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        verify(eventService, never()).save(any(Event.class)); // Garante que nenhum evento foi salvo
    }

    // --- Testes para o método exit() ---

    @Test
    @DisplayName("Deve processar a saída do carro e calcular o preço")
    void exit_WhenCarHasEntryEvent_ShouldProcessExit() {
        // Given
        Spot spot = new Spot();
        spot.setId(1L);
        spot.setSector(A);

        Event entryEvent = Event.builder().eventType(EventType.ENTRY).entryTime(LocalDate.now()).build();

        Event parkedEvent = Event.builder().eventType(EventType.PARKED).spot(spot).build();

        RegisterEvent registerEvent = RegisterEvent.builder()
                .licensePlate(car.getLicensePlate())
                .eventList(List.of(entryEvent, parkedEvent))
                .build();

        given(registerEventService.findByLicensePlateAndExitTimeIsNull(car.getLicensePlate())).willReturn(Optional.of(registerEvent));
        given(garageService.findBasePriceBySector(any())).willReturn(10.0); // Preço base de R$10
        given(spotService.findSpotOccupied()).willReturn(5); // 5 vagas ocupadas
        given(spotService.countAllSpots()).willReturn(10); // 10 vagas no total (50% de ocupação)

        // When
        ResponseEntity response = parkingService.exit(eventExitDTO, car);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verifica se os métodos de persistência foram chamados
        verify(spotService).save(any(Spot.class));
        verify(carService).save(any(Car.class));
        verify(eventService).save(any(Event.class));

        // Verifica o estado final das entidades
        assertThat(car.isInTheGarage()).isFalse();
        assertThat(car.getExitTime()).isNotNull();
        assertThat(car.getPriceUntilNow()).isGreaterThan(0); // Garante que um preço foi calculado
    }

    @Test
    @DisplayName("Não deve processar a saída de carro que não possui evento de entrada")
    void exit_WhenCarHasNoEntryEvent_ShouldReturnConflict() {
        // Given
        given(registerEventService.findByLicensePlateAndExitTimeIsNull(car.getLicensePlate())).willReturn(Optional.empty());
        //given(eventService.save(any(Event.class))).willReturn(null);
        //given(parkingService.exit(eventParkedDTO,car)).willReturn(ResponseEntity.status(HttpStatus.CONFLICT).build());
        // When
        ResponseEntity response = parkingService.exit(eventExitDTO, car);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        verify(carService, never()).save(any(Car.class)); // Garante que nenhuma entidade foi alterada
    }
}