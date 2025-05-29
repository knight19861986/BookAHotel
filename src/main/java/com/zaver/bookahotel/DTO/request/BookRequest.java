package com.zaver.bookahotel.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotNull(message = "fromDate must not be null")
    private Long fromDate;

    @NotNull(message = "toDate must not be null")
    private Long toDate;

    @NotNull(message = "roomIds must not be null")
    @Size(min = 1, message = "At least one room ID must be provided")
    private Set<Long> roomIds;
}
