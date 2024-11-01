package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.source;

import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.configuration.util.OptionRule;
import org.apache.seatunnel.api.source.SeaTunnelSource;
import org.apache.seatunnel.api.source.SourceSplit;
import org.apache.seatunnel.api.table.catalog.CatalogTable;
import org.apache.seatunnel.api.table.catalog.CatalogTableUtil;
import org.apache.seatunnel.api.table.connector.TableSource;
import org.apache.seatunnel.api.table.factory.Factory;
import org.apache.seatunnel.api.table.factory.TableSourceFactory;
import org.apache.seatunnel.api.table.factory.TableSourceFactoryContext;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfig;

import com.google.auto.service.AutoService;

import java.io.Serializable;
import java.util.List;

@AutoService(Factory.class)
public class OceanBaseSourceFactory implements TableSourceFactory {
    @Override
    public String factoryIdentifier() {
        return OceanBaseConfig.CONNECTOR_NAME;
    }

    @Override
    public OptionRule optionRule() {
        return OptionRule.builder()
                .required(
                        OceanBaseConfig.USERNAME,
                        OceanBaseConfig.PASSWORD)
                .optional(OceanBaseConfig.PORT)
                .build();
    }

    @Override
    public Class<? extends SeaTunnelSource> getSourceClass() {
        return OceanBaseSource.class;
    }



    @Override
    public <T, SplitT extends SourceSplit, StateT extends Serializable> TableSource<T, SplitT, StateT> createSource(TableSourceFactoryContext context) {
        ReadonlyConfig config = context.getOptions();
        OceanBaseConfig sourceConfig = new OceanBaseConfig(config);
        List<CatalogTable> catalogTables = CatalogTableUtil.getCatalogTables(context.getOptions(), context.getClassLoader());
        return () ->
                (SeaTunnelSource<T, SplitT, StateT>)
                        new OceanBaseSource(sourceConfig, catalogTables);
    }

}
