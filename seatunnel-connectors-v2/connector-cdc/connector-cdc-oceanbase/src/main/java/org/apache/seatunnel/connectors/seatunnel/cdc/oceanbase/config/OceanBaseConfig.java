package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config;

import io.debezium.relational.RelationalDatabaseConnectorConfig;
import lombok.*;
import org.apache.seatunnel.api.configuration.Option;
import org.apache.seatunnel.api.configuration.Options;

import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.configuration.SingleChoiceOption;
import org.apache.seatunnel.connectors.cdc.base.config.JdbcSourceConfig;
import org.apache.seatunnel.connectors.cdc.base.config.StartupConfig;
import org.apache.seatunnel.connectors.cdc.base.config.StopConfig;
import org.apache.seatunnel.connectors.cdc.base.option.SourceOptions;
import org.apache.seatunnel.connectors.cdc.base.option.StartupMode;
import org.apache.seatunnel.connectors.cdc.base.option.StopMode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/10/31--星期四--20:23 @创建项目 seatunnel @版本号 V1.0 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OceanBaseConfig implements Serializable {
    private String databaseName;
    private String tableName;
    private StartupMode startupMode;
    private Integer batchSize;
}
