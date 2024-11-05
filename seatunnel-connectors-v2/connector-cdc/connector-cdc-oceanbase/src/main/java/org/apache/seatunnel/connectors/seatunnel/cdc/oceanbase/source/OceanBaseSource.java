package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source;

import org.apache.seatunnel.api.configuration.Option;
import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.source.Boundedness;
import org.apache.seatunnel.api.source.SupportParallelism;
import org.apache.seatunnel.api.table.catalog.CatalogTable;
import org.apache.seatunnel.api.table.type.SeaTunnelDataType;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.common.utils.JdbcUrlUtil;
import org.apache.seatunnel.connectors.cdc.base.config.JdbcSourceConfig;
import org.apache.seatunnel.connectors.cdc.base.config.SourceConfig;
import org.apache.seatunnel.connectors.cdc.base.dialect.DataSourceDialect;
import org.apache.seatunnel.connectors.cdc.base.option.JdbcSourceOptions;
import org.apache.seatunnel.connectors.cdc.base.option.StartupMode;
import org.apache.seatunnel.connectors.cdc.base.option.StopMode;
import org.apache.seatunnel.connectors.cdc.base.source.IncrementalSource;
import org.apache.seatunnel.connectors.cdc.base.source.offset.OffsetFactory;
import org.apache.seatunnel.connectors.cdc.debezium.DebeziumDeserializationSchema;
import org.apache.seatunnel.connectors.cdc.debezium.row.SeaTunnelRowDebeziumDeserializeSchema;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfig;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfigFactory;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseOption;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source.offset.BinlogOffsetFactory;

import java.time.ZoneId;
import java.util.List;

/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/10/31--星期四--20:04 @创建项目 seatunnel @版本号 V1.0 */
public class OceanBaseSource<T> extends IncrementalSource<T, JdbcSourceConfig>
        implements SupportParallelism {

    private List<CatalogTable> catalogTable;
    private OceanBaseConfig sourceConfig;

    public OceanBaseSource(
            ReadonlyConfig options,
            SeaTunnelDataType<SeaTunnelRow> dataType,
            List<CatalogTable> catalogTables) {
        super(options, dataType, catalogTables);
        this.catalogTable = catalogTables;
    }

    @Override
    public String getPluginName() {
        return OceanBaseOption.CONNECTOR_NAME;
    }

    @Override
    public List<CatalogTable> getProducedCatalogTables() {
        return catalogTable;
    }

    @Override
    public Option<StartupMode> getStartupModeOption() {
        return OceanBaseOption.STARTUP_MODE;
    }

    @Override
    public Option<StopMode> getStopModeOption() {
        return OceanBaseOption.STOP_MODE;
    }

    /*
     * 必须要实现的
     * */
    @Override
    public SourceConfig.Factory<JdbcSourceConfig> createSourceConfigFactory(ReadonlyConfig config) {
        OceanBaseConfigFactory configFactory = new OceanBaseConfigFactory();
        configFactory.serverId(config.get(JdbcSourceOptions.SERVER_ID));
        configFactory.fromReadonlyConfig(readonlyConfig);
        JdbcUrlUtil.UrlInfo urlInfo = JdbcUrlUtil.getUrlInfo(config.get(OceanBaseOption.BASE_URL));
        configFactory.originUrl(urlInfo.getOrigin());
        configFactory.hostname(urlInfo.getHost());
        configFactory.port(urlInfo.getPort());
        configFactory.startupOptions(startupConfig);
        configFactory.stopOptions(stopConfig);
        return configFactory;
    }

    // @Override
    // public SourceReader<T, SourceSplitBase> createReader(SourceReader.Context readerContext)
    // throws Exception {
    //     return super.createReader(readerContext);
    // }

    /*
     * 必须要实现的
     * */
    @Override
    public DebeziumDeserializationSchema<T> createDebeziumDeserializationSchema(
            ReadonlyConfig config) {
        SeaTunnelDataType<SeaTunnelRow> physicalRowType = dataType;
        String zoneId = config.get(JdbcSourceOptions.SERVER_TIME_ZONE);
        return (DebeziumDeserializationSchema<T>)
                SeaTunnelRowDebeziumDeserializeSchema.builder()
                        .setPhysicalRowType(physicalRowType)
                        .setResultTypeInfo(physicalRowType)
                        .setServerTimeZone(ZoneId.of(zoneId))
                        .setSchemaChangeResolver(
                                new OceanBaseSchemaChangeResolver(
                                        createSourceConfigFactory(config)))
                        .build();
    }

    @Override
    public DataSourceDialect<JdbcSourceConfig> createDataSourceDialect(ReadonlyConfig config) {
        return new OceanBaseDialect((OceanBaseConfigFactory) configFactory, catalogTables);
    }

    @Override
    public OffsetFactory createOffsetFactory(ReadonlyConfig config) {
        return new BinlogOffsetFactory(
                (OceanBaseConfigFactory) configFactory, (OceanBaseDialect) dataSourceDialect);
    }

    /**
     * @param
     * @return org.apache.seatunnel.api.source.Boundedness
     * @apiNote TODO
     * @author Z.J.Lee @创建时间 2024/10/31--21:06
     */
    @Override
    public Boundedness getBoundedness() {
        return Boundedness.UNBOUNDED;
    }
}
