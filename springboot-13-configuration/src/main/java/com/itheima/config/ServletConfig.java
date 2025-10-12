package com.itheima.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Data
@Component
@ConfigurationProperties(prefix = "servlet")
public class ServletConfig {
    private String ipAddress;
    private String port;
    private long timeout;

    @DurationUnit(ChronoUnit.HOURS)
    private Duration severTimeOut;

    @DataSizeUnit(DataUnit.KILOBYTES)
    private DataSize dataSize;
}
