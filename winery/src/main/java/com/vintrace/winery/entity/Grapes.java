package com.vintrace.winery.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityScan
public class Grapes {

    private String variety;
    // year as integer due to the fact that only single figures are taken into consideration
    private long year;
    private String region;
    private double percentage;
}
