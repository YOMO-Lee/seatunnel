package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source;

import org.apache.seatunnel.api.source.Collector;
import org.apache.seatunnel.api.source.SourceReader;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.api.table.type.SeaTunnelRowType;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfig;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/10/31--星期四--20:05 @创建项目 seatunnel @版本号 V1.0 */
@Slf4j
public class OceanBaseSourceReader implements SourceReader<SeaTunnelRow, OceanBaseSourceSplit> {

    public OceanBaseSourceReader(
            Context readerContext,
            SeaTunnelRowType seaTunnelRowType,
            OceanBaseConfig sourceConfig) {
        // this.pendingSplits = new LinkedList<>();
        // this.context = readerContext;
        // this.sourceConfig = sourceConfig;
        // this.seaTunnelRowType = seaTunnelRowType;
    }

    @Override
    public void open() throws Exception {}

    @Override
    public void close() throws IOException {}

    @Override
    public void pollNext(Collector<SeaTunnelRow> output) throws Exception {

        log.info("开始了");
    }

    @Override
    public List<OceanBaseSourceSplit> snapshotState(long checkpointId) throws Exception {
        return null;
    }

    @Override
    public void addSplits(List<OceanBaseSourceSplit> splits) {}

    @Override
    public void handleNoMoreSplits() {}

    @Override
    public void notifyCheckpointComplete(long checkpointId) throws Exception {}
}
