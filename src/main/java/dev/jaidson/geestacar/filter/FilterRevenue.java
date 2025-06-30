package dev.jaidson.geestacar.filter;

import dev.jaidson.geestacar.enums.Sector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterRevenue {
    private LocalDate date;
    private Sector sector;


}
