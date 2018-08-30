package com.lwxf.newstore.webapp.domain.entity.user;

import java.sql.Types;
import java.util.*;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.DataValidatorUtils;
import com.lwxf.commons.utils.ValidateUtils;
import com.lwxf.mybatis.annotation.Column;
import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.utils.TypesExtend;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;

/**
 *功能：user_resetpass 实体类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-05-25 09:52
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "user_resetpass", displayName = "user_resetpass")
public class UserResetpass extends IdEntity {
	transient final static List<String> propertiesList = Arrays.asList("id", "created", "email");
	private static final long serialVersionUID = 3636355946951060207L;
	@Column(type = TypesExtend.DATETIME, nullable = false, name = "created", displayName = "")
	private Date created;
	@Column(type = Types.VARCHAR, length = 50, nullable = false, name = "email", displayName = "")
	private String email;

	public UserResetpass() {
	}

	public static RequestResult validateFields(MapContext map) {
		Map<String, String> validResult = new HashMap<>();
		if (map.size() == 0) {
			return ResultFactory.generateErrorResult("error", ErrorCodes.VALIDATE_NOTNULL);
		}
		boolean flag;
		Set<String> mapSet = map.keySet();
		flag = propertiesList.containsAll(mapSet);
		if (!flag) {
			return ResultFactory.generateErrorResult("error", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		}
		if (map.containsKey("created")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("created", String.class))) {
				validResult.put("created", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if (map.containsKey("created")) {
			if (map.get("created") == null) {
				validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if (map.containsKey("email")) {
			if (map.getTypedValue("email", String.class) == null) {
				validResult.put("email", ErrorCodes.VALIDATE_NOTNULL);
			} else {
				if (LwxfStringUtils.getStringLength(map.getTypedValue("email", String.class)) > 50) {
					validResult.put("email", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				} else if (!ValidateUtils.isEmail(map.getTypedValue("email", String.class))) {
					validResult.put("email", ErrorCodes.VALIDATE_INVALID_EMAIL);
			}
			}
		}
		if (validResult.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, validResult);
		} else {
			return null;
		}
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.created == null) {
			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.email == null) {
			validResult.put("email", ErrorCodes.VALIDATE_NOTNULL);
		} else {
			if (LwxfStringUtils.getStringLength(this.email) > 50) {
				validResult.put("email", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			} else if (!ValidateUtils.isEmail(this.email)) {
				validResult.put("email", ErrorCodes.VALIDATE_INVALID_EMAIL);
			}
		}
		if (validResult.size() > 0) {
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, validResult);
		} else {
			return null;
		}
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

