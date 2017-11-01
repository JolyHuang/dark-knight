package com.sharingif.cube.dark.knight.collection.file.handler;

/**
 * 保存group index 和数据key
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午7:15
 */
public class GroupData {

    public GroupData(int groupIndex, String dataKey) {
        this.groupIndex = groupIndex;
        this.dataKey = dataKey;
        this.type = String.class;
    }

    public GroupData(int groupIndex, String dataKey, Class type) {
        this.groupIndex = groupIndex;
        this.dataKey = dataKey;
        this.type = type;
    }

    private int groupIndex;
    private String dataKey;
    private Class type;

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroupData{");
        sb.append("groupIndex=").append(groupIndex);
        sb.append(", dataKey='").append(dataKey).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
