package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.catalog;

import org.apache.seatunnel.api.configuration.util.OptionRule;
import org.apache.seatunnel.api.source.SeaTunnelSource;
import org.apache.seatunnel.api.table.factory.TableSourceFactory;

import lombok.extern.slf4j.Slf4j;

/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/11/2--星期六--23:32 @创建项目 seatunnel @版本号 V1.0 */
@Slf4j
public class OceanBaseCatalog implements TableSourceFactory {

    private static final String SELECT_COLUMNS_SQL_TEMPLATE =
            "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME ='%s' ORDER BY ORDINAL_POSITION ASC";

    private static final String SELECT_DATABASE_EXISTS =
            "SELECT SCHEMA_NAME FROM information_schema.schemata WHERE SCHEMA_NAME = '%s'";

    private static final String SELECT_TABLE_EXISTS =
            "SELECT TABLE_SCHEMA,TABLE_NAME FROM information_schema.tables WHERE table_schema = '%s' AND table_name = '%s'";

    @Override
    public String factoryIdentifier() {
        return null;
    }

    @Override
    public OptionRule optionRule() {
        return null;
    }

    @Override
    public Class<? extends SeaTunnelSource> getSourceClass() {
        return null;
    }
}
