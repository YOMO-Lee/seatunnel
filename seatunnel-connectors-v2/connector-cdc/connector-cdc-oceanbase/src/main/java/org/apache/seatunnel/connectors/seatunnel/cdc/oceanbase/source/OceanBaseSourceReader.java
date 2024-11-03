package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source;

import org.apache.seatunnel.api.source.Collector;
import org.apache.seatunnel.api.source.SourceReader;
import org.apache.seatunnel.api.table.catalog.CatalogTable;
import org.apache.seatunnel.api.table.catalog.TablePath;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfig;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/10/31--星期四--20:05 @创建项目 seatunnel @版本号 V1.0 */
@Slf4j
public class OceanBaseSourceReader implements SourceReader<SeaTunnelRow, OceanBaseSourceSplit> {

    private final Context context;
    private final Deque<OceanBaseSourceSplit> splits = new ConcurrentLinkedDeque<>();

    public OceanBaseSourceReader(
            Context context, OceanBaseConfig config, Map<TablePath, CatalogTable> tables) {
        this.context = context;
    }

    @Override
    public void open() throws Exception {}

    @Override
    public void close() throws IOException {}

    @Override
    public void pollNext(Collector<SeaTunnelRow> output) throws Exception {

        log.info("开始了");
        synchronized (output.getCheckpointLock()) {
            OceanBaseSourceSplit split = splits.poll();
            System.out.println(split);
            // if (null != split) {
            //     try {
            //         inputFormat.open(split);
            //         while (!inputFormat.reachedEnd()) {
            //             SeaTunnelRow seaTunnelRow = inputFormat.nextRecord();
            //             output.collect(seaTunnelRow);
            //         }
            //     } finally {
            //         inputFormat.close();
            //     }
            // } else if (noMoreSplit && splits.isEmpty()) {
            //     // signal to the source that we have reached the end of the data.
            //     log.info("Closed the bounded jdbc source");
            //     context.signalNoMoreElement();
            // } else {
            //     Thread.sleep(1000L);
            // }
        }
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
