package com.lwxf.newstore.webapp.domain.entity.base;

import java.sql.Types;

import com.lwxf.mybatis.annotation.Id;

/**
 * 功能：普通实体的id基类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:09:53
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class IdEntity extends AbstractIdEntity<String> {
	@Id(type = Types.CHAR, length = 13, auto = false)
	public String id;
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		IdEntity idEntity = (IdEntity) o;

		return id != null ? id.equals(idEntity.id) : idEntity.id == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (id != null ? id.hashCode() : 0);
		return result;
	}
}
