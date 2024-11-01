package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config;

import lombok.Data;
import org.apache.seatunnel.api.configuration.Option;
import org.apache.seatunnel.api.configuration.Options;

import org.apache.seatunnel.api.configuration.ReadonlyConfig;


/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/10/31--星期四--20:23 @创建项目 seatunnel @版本号 V1.0 */
@Data
public class OceanBaseConfig {

    public static final String CONNECTOR_NAME = "OceanBase-CDC";

    public OceanBaseConfig(ReadonlyConfig config) {
    }

    public static final Option<String> HOST =
            Options.key("host")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("OceanBase server host");

    public static final Option<Integer> PORT =
            Options.key("port").intType().noDefaultValue().withDescription("OceanBase server port");

    public static final Option<String> DATABASE =
            Options.key("database")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("OceanBase database name");

    public static final Option<String> USERNAME =
            Options.key("username")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("OceanBase server username");

    public static final Option<String> PASSWORD =
            Options.key("password")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("OceanBase server password");
}
