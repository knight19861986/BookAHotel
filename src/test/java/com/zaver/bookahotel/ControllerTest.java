package com.zaver.bookahotel;

import com.zaver.bookahotel.DTO.BookingDTO;
import com.zaver.bookahotel.DTO.RoomDTO;
import com.zaver.bookahotel.repo.BookingRepository;
import com.zaver.bookahotel.repo.RoomRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    private static final String BASE_URL = "/zaver/test/api/v1";
    private static final String ROOMS = "/rooms";
    private static final String BOOKINGS = "/bookings";
    private static final String BOOK = "/book";
    private static final String SLASH = "/";

    private String getUrl(String path) {
        return "http://localhost:" + port + BASE_URL + path;
    }

    private HttpEntity<Map<String, Object>> jsonEntity(Map<String, Object> payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(payload, headers);
    }

    @Test
    @Order(1)
    public void testSearchRooms() {
        String url = getUrl(ROOMS);

        ResponseEntity<List<RoomDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(8);
    }

    @Test
    @Order(2)
    public void testBookRoom() {
        String url = getUrl(BOOK);
        Map<String, Object> payload = new HashMap<>();
        payload.put("fromDate", LocalDate.now().plusDays(7));
        payload.put("toDate", LocalDate.now().plusDays(10));
        payload.put("roomIds", Arrays.asList(1L, 2L));
        HttpEntity<Map<String, Object>> entity = jsonEntity(payload);

        ResponseEntity<Long> postResponse = restTemplate.postForEntity(
                url,
                payload,
                Long.class
        );

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isEqualTo(1L);

        url = UriComponentsBuilder.fromHttpUrl(getUrl(ROOMS))
                .queryParam("dateFrom", LocalDate.now().plusDays(8))
                .queryParam("dateTo", LocalDate.now().plusDays(11))
                .queryParam("numberOfBeds", 1)
                .toUriString();

        ResponseEntity<List<RoomDTO>> getResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().size()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void testSearchBookings() {
        String url = UriComponentsBuilder.fromHttpUrl(getUrl(BOOKINGS))
                .queryParam("dateFrom", LocalDate.now().plusDays(8))
                .queryParam("dateTo", LocalDate.now().plusDays(11))
                .toUriString();

        ResponseEntity<List<BookingDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void testCancelBooking() {
        String url = getUrl(BOOK + SLASH + String.valueOf(1L));

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        url = getUrl(BOOKINGS);

        ResponseEntity<List<BookingDTO>> getResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().isEmpty());
    }
}
