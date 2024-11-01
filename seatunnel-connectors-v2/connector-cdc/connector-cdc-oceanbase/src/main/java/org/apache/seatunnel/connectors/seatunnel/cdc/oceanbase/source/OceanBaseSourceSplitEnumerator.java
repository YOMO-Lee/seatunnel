package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source;

import org.apache.seatunnel.api.source.SourceSplitEnumerator;

import java.io.IOException;
import java.util.List;

/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/10/31--星期四--20:29 @创建项目 seatunnel @版本号 V1.0 */
public class OceanBaseSourceSplitEnumerator
        implements SourceSplitEnumerator<OceanBaseSourceSplit, OceanBaseSourceState> {
    @Override
    public void open() {}

    @Override
    public void run() throws Exception {}

    @Override
    public void close() throws IOException {}

    @Override
    public void addSplitsBack(List<OceanBaseSourceSplit> splits, int subtaskId) {}

    @Override
    public int currentUnassignedSplitSize() {
        return 0;
    }

    @Override
    public void handleSplitRequest(int subtaskId) {}

    @Override
    public void registerReader(int subtaskId) {}

    @Override
    public OceanBaseSourceState snapshotState(long checkpointId) throws Exception {
        return null;
    }

    @Override
    public void notifyCheckpointComplete(long checkpointId) throws Exception {}
}
