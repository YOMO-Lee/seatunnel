package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase;

import com.google.auto.service.AutoService;
import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.configuration.util.OptionRule;
import org.apache.seatunnel.api.table.catalog.Catalog;
import org.apache.seatunnel.api.table.factory.CatalogFactory;
import org.apache.seatunnel.api.table.factory.Factory;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseConfig;

/**
 * @类描述 TODO
 * @创建作者 Z.J.Lee
 * @创建团队 TCL格创东智科技有限公司
 * @创建时间 2024/11/1--星期五--下午3:38
 * @创建项目 seatunnel
 * @版本号 V1.0
 */
@AutoService(Factory.class)
public class OceanBaseCatalog implements CatalogFactory {


    @Override
    public Catalog createCatalog(String catalogName, ReadonlyConfig options) {

        System.out.println(catalogName);
        System.out.println(options);
        return null;
    }

    @Override
    public String factoryIdentifier() {
        return OceanBaseConfig.CONNECTOR_NAME;
    }

    @Override
    public OptionRule optionRule() {
        return null;
    }
}
