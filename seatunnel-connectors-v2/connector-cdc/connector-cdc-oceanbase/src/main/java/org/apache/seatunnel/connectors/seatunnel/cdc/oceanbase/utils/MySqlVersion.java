package org.apache.seatunnel.connectors.seatunnel.cdc.oceanbase.utils;

/**
 * @类描述 TODO @创建作者 Z.J.Lee @创建团队 TCL格创东智科技有限公司 @创建时间 2024/11/4--星期一--下午3:23 @创建项目 seatunnel @版本号
 * V1.0
 */
public enum MySqlVersion {
    V_5_5("5.5"),
    V_5_6("5.6"),
    V_5_7("5.7"),
    V_8("8.0"),
    V_8_1("8.1"),
    V_8_2("8.2"),
    V_8_3("8.3"),
    V_8_4("8.4");

    private final String versionPrefix;

    MySqlVersion(String versionPrefix) {
        this.versionPrefix = versionPrefix;
    }

    public static MySqlVersion parse(String version) {
        if (version != null) {
            for (MySqlVersion mySqlVersion : values()) {
                if (version.startsWith(mySqlVersion.versionPrefix)) {
                    return mySqlVersion;
                }
            }
        }
        throw new UnsupportedOperationException("Unsupported MySQL version: " + version);
    }

    public boolean isBefore(MySqlVersion version) {
        return this.compareTo(version) < 0;
    }

    public boolean isAtOrBefore(MySqlVersion version) {
        return this.compareTo(version) <= 0;
    }

    public boolean isAfter(MySqlVersion version) {
        return this.compareTo(version) > 0;
    }

    public boolean isAtOrAfter(MySqlVersion version) {
        return this.compareTo(version) >= 0;
    }
}
