package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config;

import org.apache.seatunnel.api.configuration.Option;
import org.apache.seatunnel.api.configuration.Options;
import org.apache.seatunnel.api.configuration.SingleChoiceOption;
import org.apache.seatunnel.api.configuration.util.OptionRule;
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

    public static final Option<String> BASE_URL =
            Options.key("base-url")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "URL has to be with database, like \"jdbc:mysql://localhost:5432/db\" or"
                                    + "\"jdbc:mysql://localhost:5432/db?useSSL=true\".");

    public static final Option<String> USERNAME =
            Options.key("username")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "Name of the database to use when connecting to the database server.");

    public static final Option<String> PASSWORD =
            Options.key("password")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Password to use when connecting to the database server.");

    public static final Option<String> SCHEMA =
            Options.key("schema")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "for databases that support the schema parameter, give it priority.");

    public static final Option<String> COMPATIBLE_MODE =
            Options.key("compatibleMode")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "The compatible mode of database, required when the database supports multiple compatible modes. "
                                    + "For example, when using OceanBase database, you need to set it to 'mysql' or 'oracle'.");

    // OptionRule.Builder BASE_RULE =
    //         OptionRule.builder()
    //                 .required(BASE_URL)
    //                 .required(USERNAME, PASSWORD)
    //                 .optional(SCHEMA, JdbcOptions.DECIMAL_TYPE_NARROWING);

    public static final Option<String> TABLE_PREFIX =
            Options.key("tablePrefix")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "The table prefix name added when the table is automatically created");

    public static final Option<String> TABLE_SUFFIX =
            Options.key("tableSuffix")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "The table suffix name added when the table is automatically created");

    public static final Option<Boolean> CREATE_INDEX =
            Options.key("create_index")
                    .booleanType()
                    .defaultValue(true)
                    .withDescription("Create index or not when auto create table");

    public static final SingleChoiceOption<StartupMode> STARTUP_MODE =
            (SingleChoiceOption)
                    Options.key(SourceOptions.STARTUP_MODE_KEY)
                            .singleChoice(
                                    StartupMode.class,
                                    Arrays.asList(
                                            StartupMode.INITIAL,
                                            StartupMode.EARLIEST,
                                            StartupMode.LATEST,
                                            StartupMode.SPECIFIC))
                            .defaultValue(StartupMode.INITIAL)
                            .withDescription(
                                    "Optional startup mode for CDC source, valid enumerations are "
                                            + "\"initial\", \"earliest\", \"latest\" or \"specific\"");

    public static final SingleChoiceOption<StopMode> STOP_MODE =
            (SingleChoiceOption)
                    Options.key(SourceOptions.STOP_MODE_KEY)
                            .singleChoice(
                                    StopMode.class,
                                    Arrays.asList(
                                            StopMode.LATEST, StopMode.SPECIFIC, StopMode.NEVER))
                            .defaultValue(StopMode.NEVER)
                            .withDescription(
                                    "Optional stop mode for CDC source, valid enumerations are "
                                            + "\"never\", \"latest\" or \"specific\"");


}
