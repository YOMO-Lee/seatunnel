package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source;

import org.apache.seatunnel.api.table.catalog.CatalogTable;
import org.apache.seatunnel.common.utils.SeaTunnelException;
import org.apache.seatunnel.connectors.cdc.base.config.JdbcSourceConfig;
import org.apache.seatunnel.connectors.cdc.base.dialect.JdbcDataSourceDialect;
import org.apache.seatunnel.connectors.cdc.base.source.enumerator.splitter.ChunkSplitter;
import org.apache.seatunnel.connectors.cdc.base.source.reader.external.FetchTask;
import org.apache.seatunnel.connectors.cdc.base.source.reader.external.JdbcSourceFetchTaskContext;
import org.apache.seatunnel.connectors.cdc.base.source.split.SourceSplitBase;
import org.apache.seatunnel.connectors.cdc.base.utils.CatalogTableUtils;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfig;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfigFactory;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseOption;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source.enumerator.OceanBaseChunkSplitter;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source.reader.OceanBaseBinlogFetchTask;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source.reader.OceanBaseSnapshotFetchTask;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.utils.OceanBaseConnectionUtils;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.utils.OceanBaseSchema;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.utils.TableDiscoveryUtils;

import io.debezium.jdbc.JdbcConnection;
import io.debezium.relational.TableId;
import io.debezium.relational.history.TableChanges;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.utils.OceanBaseConnectionUtils.isTableIdCaseSensitive;

/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/11/2--星期六--19:40 @创建项目 seatunnel @版本号 V1.0 */
public class OceanBaseDialect implements JdbcDataSourceDialect {

    private final OceanBaseConfig sourceConfig;
    private transient OceanBaseSchema mySqlSchema;
    private final Map<TableId, CatalogTable> tableMap;

    public OceanBaseDialect(
            OceanBaseConfigFactory configFactory, List<CatalogTable> catalogTables) {
        this.sourceConfig = configFactory.create(0);
        this.tableMap = CatalogTableUtils.convertTables(catalogTables);
    }

    @Override
    public String getName() {
        return OceanBaseOption.CONNECTOR_NAME;
    }

    @Override
    public boolean isDataCollectionIdCaseSensitive(JdbcSourceConfig sourceConfig) {
        try (JdbcConnection jdbcConnection = openJdbcConnection(sourceConfig)) {
            return isDataCollectionIdCaseSensitive(jdbcConnection);
        } catch (SQLException e) {
            throw new SeaTunnelException("Error reading MySQL variables: " + e.getMessage(), e);
        }
    }

    private boolean isDataCollectionIdCaseSensitive(JdbcConnection jdbcConnection) {
        return isTableIdCaseSensitive(jdbcConnection);
    }

    @Override
    public ChunkSplitter createChunkSplitter(JdbcSourceConfig sourceConfig) {
        return new OceanBaseChunkSplitter(sourceConfig, this);
    }

    @Override
    public List<TableId> discoverDataCollections(JdbcSourceConfig sourceConfig) {
        OceanBaseConfig mySqlSourceConfig = (OceanBaseConfig) sourceConfig;
        try (JdbcConnection jdbcConnection = openJdbcConnection(sourceConfig)) {
            return TableDiscoveryUtils.listTables(
                    jdbcConnection, mySqlSourceConfig.getTableFilters());
        } catch (SQLException e) {
            throw new SeaTunnelException("Error to discover tables: " + e.getMessage(), e);
        }
    }

    @Override
    public JdbcConnection openJdbcConnection(JdbcSourceConfig sourceConfig) {
        return OceanBaseConnectionUtils.createMySqlConnection(sourceConfig.getDbzConfiguration());
    }

    @Override
    public TableChanges.TableChange queryTableSchema(JdbcConnection jdbc, TableId tableId) {
        if (mySqlSchema == null) {
            mySqlSchema =
                    new OceanBaseSchema(
                            sourceConfig, isDataCollectionIdCaseSensitive(jdbc), tableMap);
        }
        return mySqlSchema.getTableSchema(jdbc, tableId);
    }

    // 这里不能为null
    @Override
    public FetchTask<SourceSplitBase> createFetchTask(SourceSplitBase sourceSplitBase) {
        if (sourceSplitBase.isSnapshotSplit()) {
            return new OceanBaseSnapshotFetchTask(sourceSplitBase.asSnapshotSplit());
        } else {
            return new OceanBaseBinlogFetchTask(sourceSplitBase.asIncrementalSplit());
        }
    }

    @Override
    public JdbcSourceFetchTaskContext createFetchTaskContext(
            SourceSplitBase sourceSplitBase, JdbcSourceConfig taskSourceConfig) {
        return new OceanBaseSourceFetchTaskContext(taskSourceConfig, this);
    }
}
