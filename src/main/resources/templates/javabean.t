package com.dianping.hui.order.shard.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lipeng on 16/7/12.
 */
public class HuiOrderDetailEntity implements Serializable {
    private static final long serialVersionUID = -1;
    /**自增id*/
    private long id ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
<%
for(f in c.tableInfo.fields){
        println(f.name);

}
%>
