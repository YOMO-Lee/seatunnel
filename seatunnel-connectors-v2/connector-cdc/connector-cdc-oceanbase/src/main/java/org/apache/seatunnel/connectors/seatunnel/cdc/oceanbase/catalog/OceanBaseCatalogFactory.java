package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.catalog;

import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.configuration.util.OptionRule;
import org.apache.seatunnel.api.table.catalog.Catalog;
import org.apache.seatunnel.api.table.factory.CatalogFactory;
import org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.config.OceanBaseOption;

/** @类描述 TODO @创建作者 YOMO Lee @创建团队 TCL格创东智 @创建时间 2024/11/2--星期六--22:50 @创建项目 seatunnel @版本号 V1.0 */
public class OceanBaseCatalogFactory implements CatalogFactory {

    @Override
    public String factoryIdentifier() {
        return OceanBaseOption.CONNECTOR_NAME;
    }

    @Override
    public Catalog createCatalog(String catalogName, ReadonlyConfig options) {
        return null;
        // String urlWithDatabase = options.get(JdbcCatalogOptions.BASE_URL);
        // Preconditions.checkArgument(
        //         StringUtils.isNoneBlank(urlWithDatabase),
        //         "Miss config <base-url>! Please check your config.");
        // JdbcUrlUtil.UrlInfo urlInfo = JdbcUrlUtil.getUrlInfo(urlWithDatabase);
        // Optional<String> defaultDatabase = urlInfo.getDefaultDatabase();
        // if (!defaultDatabase.isPresent()) {
        //     throw new OptionValidationException(JdbcCatalogOptions.BASE_URL);
        // }
        //
        // String compatibleMode = options.get(JdbcCatalogOptions.COMPATIBLE_MODE);
        // Preconditions.checkArgument(
        //         StringUtils.isNoneBlank(compatibleMode),
        //         "Miss config <compatibleMode>! Please check your config.");
        //
        // if ("oracle".equalsIgnoreCase(compatibleMode.trim())) {
        //     return new OceanBaseOracleCatalog(
        //             catalogName,
        //             options.get(JdbcCatalogOptions.USERNAME),
        //             options.get(JdbcCatalogOptions.PASSWORD),
        //             urlInfo,
        //             options.get(JdbcCatalogOptions.SCHEMA));
        // }
        // return new OceanBaseMySqlCatalog(
        //         catalogName,
        //         options.get(JdbcCatalogOptions.USERNAME),
        //         options.get(JdbcCatalogOptions.PASSWORD),
        //         urlInfo);
    }

    @Override
    public OptionRule optionRule() {
        return null;
        // return JdbcCatalogOptions.BASE_RULE.required(JdbcCatalogOptions.COMPATIBLE_MODE).build();
    }
}
