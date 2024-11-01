package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source;

import org.apache.seatunnel.api.source.Boundedness;
import org.apache.seatunnel.api.source.SeaTunnelSource;
import org.apache.seatunnel.api.source.SourceReader;
import org.apache.seatunnel.api.source.SourceSplitEnumerator;
import org.apache.seatunnel.api.table.catalog.CatalogTable;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfig;

import java.util.List;

/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/10/31--星期四--20:04 @创建项目 seatunnel @版本号 V1.0 */
public class OceanBaseSource
        implements SeaTunnelSource<SeaTunnelRow, OceanBaseSourceSplit, OceanBaseSourceState> {

    private List<CatalogTable> catalogTable;
    private OceanBaseConfig sourceConfig;

    public OceanBaseSource(OceanBaseConfig sourceConfig, List<CatalogTable> catalogTable) {
        this.sourceConfig = sourceConfig;
        this.catalogTable = catalogTable;
    }

    @Override
    public String getPluginName() {
        return OceanBaseConfig.CONNECTOR_NAME;
    }

    @Override
    public List<CatalogTable> getProducedCatalogTables() {
        return SeaTunnelSource.super.getProducedCatalogTables();
    }

    /**
     * @apiNote TODO
     * @author Z.J.Lee @创建时间 2024/10/31--21:06
     * @param
     * @return org.apache.seatunnel.api.source.Boundedness
     */
    @Override
    public Boundedness getBoundedness() {
        return Boundedness.UNBOUNDED;
    }

    /**
     * @apiNote TODO
     * @author Z.J.Lee @创建时间 2024/10/31--21:07
     * @param readerContext:
     * @return
     *     org.apache.seatunnel.api.source.SourceReader<org.apache.seatunnel.api.table.type.SeaTunnelRow,org.apache.seatunnel.connectors.seatunnel.oceanbase.source.OceanBaseSourceSplit>
     */
    @Override
    public SourceReader<SeaTunnelRow, OceanBaseSourceSplit> createReader(
            SourceReader.Context readerContext) throws Exception {
        return new OceanBaseSourceReader(
                readerContext, catalogTable.get(0).getSeaTunnelRowType(), sourceConfig);
    }

    @Override
    public SourceSplitEnumerator<OceanBaseSourceSplit, OceanBaseSourceState> createEnumerator(
            SourceSplitEnumerator.Context<OceanBaseSourceSplit> enumeratorContext)
            throws Exception {
        return null;
    }

    @Override
    public SourceSplitEnumerator<OceanBaseSourceSplit, OceanBaseSourceState> restoreEnumerator(
            SourceSplitEnumerator.Context<OceanBaseSourceSplit> enumeratorContext,
            OceanBaseSourceState checkpointState)
            throws Exception {
        return null;
    }
}
