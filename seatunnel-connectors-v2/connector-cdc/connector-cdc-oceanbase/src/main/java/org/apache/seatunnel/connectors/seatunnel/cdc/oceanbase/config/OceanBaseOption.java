package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config;

import io.debezium.relational.RelationalDatabaseConnectorConfig;
import org.apache.seatunnel.api.configuration.Option;
import org.apache.seatunnel.api.configuration.Options;
import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.configuration.SingleChoiceOption;
import org.apache.seatunnel.connectors.cdc.base.option.SourceOptions;
import org.apache.seatunnel.connectors.cdc.base.option.StartupMode;
import org.apache.seatunnel.connectors.cdc.base.option.StopMode;

import java.io.Serializable;
import java.util.Arrays;


/**
 * @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/10/31--星期四--20:23 @创建项目 seatunnel @版本号 V1.0
 */
public class OceanBaseOption implements Serializable {

    public static final String CONNECTOR_NAME = "OceanBase-CDC";

    public static final Option<String> DATABASE_NAME =
            Options.key("database-name")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Database name of the TiDB server to monitor.");

    public static final Option<String> TABLE_NAME =
            Options.key("table-name")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Table name of the database to monitor.");

    public static final Option<StartupMode> STARTUP_MODE =
            Options.key(SourceOptions.STARTUP_MODE_KEY)
                    .singleChoice(
                            StartupMode.class,
                            Arrays.asList(
                                    StartupMode.INITIAL, StartupMode.EARLIEST, StartupMode.LATEST))
                    .defaultValue(StartupMode.INITIAL)
                    .withDescription(
                            "Optional startup mode for CDC source, valid enumerations are "
                                    + "\"initial\", \"earliest\", \"latest\"");

    public static final Option<String> PD_ADDRESSES =
            Options.key("pd-addresses")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("TiKV cluster's PD address");

    public static final Option<Integer> BATCH_SIZE_PER_SCAN =
            Options.key("batch-size-per-sca ")
                    .intType()
                    .defaultValue(1000)
                    .withDescription("Size per scan");
    //
    // public static final Option<Long> TIKV_GRPC_TIMEOUT =
    //         Options.key(ConfigUtils.TIKV_GRPC_TIMEOUT)
    //                 .longType()
    //                 .noDefaultValue()
    //                 .withDescription("TiKV GRPC timeout in ms");
    //
    // public static final Option<Long> TIKV_GRPC_SCAN_TIMEOUT =
    //         Options.key(ConfigUtils.TIKV_GRPC_SCAN_TIMEOUT)
    //                 .longType()
    //                 .noDefaultValue()
    //                 .withDescription("TiKV GRPC scan timeout in ms");
    //
    // public static final Option<Integer> TIKV_BATCH_GET_CONCURRENCY =
    //         Options.key(ConfigUtils.TIKV_BATCH_GET_CONCURRENCY)
    //                 .intType()
    //                 .noDefaultValue()
    //                 .withDescription("TiKV GRPC batch get concurrency");
    //
    // public static final Option<Integer> TIKV_BATCH_SCAN_CONCURRENCY =
    //         Options.key(ConfigUtils.TIKV_BATCH_SCAN_CONCURRENCY)
    //                 .intType()
    //                 .noDefaultValue()
    //                 .withDescription("TiKV GRPC batch scan concurrency");
    //
    // public static TiConfiguration getTiConfiguration(final ReadonlyConfig configuration) {
    //     final String pdAddrsStr = configuration.get(PD_ADDRESSES);
    //     final TiConfiguration tiConf = TiConfiguration.createDefault(pdAddrsStr);
    //     configuration.getOptional(TIKV_GRPC_TIMEOUT).ifPresent(tiConf::setTimeout);
    //     configuration.getOptional(TIKV_GRPC_SCAN_TIMEOUT).ifPresent(tiConf::setScanTimeout);
    //     configuration
    //             .getOptional(TIKV_BATCH_GET_CONCURRENCY)
    //             .ifPresent(tiConf::setBatchGetConcurrency);
    //
    //     configuration
    //             .getOptional(TIKV_BATCH_SCAN_CONCURRENCY)
    //             .ifPresent(tiConf::setBatchScanConcurrency);
    //     return tiConf;
    // }

}
