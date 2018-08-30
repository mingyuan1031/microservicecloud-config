package com.lwxf.newstore.webapp.common.shiro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.util.StringUtils;

import com.lwxf.commons.exception.LwxfIllegalArgumentException;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.WebConstant;

/**
 * 功能：自定义验证器
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:30:24
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfWildcarkPermission implements Permission, Serializable {
	private Map<String,String> parts;
	protected LwxfWildcarkPermission() {
	}

	public LwxfWildcarkPermission(String wildcardString) {
		this.setParts(wildcardString);
	}


	protected void setParts(String wildcardString) {
		wildcardString = StringUtils.clean(wildcardString);

		if (LwxfStringUtils.isBlank(wildcardString)) {
			throw new LwxfIllegalArgumentException("Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.");
		}

		this.parts = new HashMap<>();
		if(ShiroUtil.WILDCARD_TOKEN.equals(wildcardString)){
			this.parts.put(ShiroUtil.WILDCARD_TOKEN,wildcardString);
			return;
		}
		String[] parts = wildcardString.split(ShiroUtil.PERMISSION_PART_DIVIDER_TOKEN);
		if(parts.length != 2){
			throw new LwxfIllegalArgumentException("Wildcard string format is error. Make sure permission strings are properly formatted.");
		}
		this.parts.put(parts[0],parts[1]);
	}

	protected Map<String,String> getParts() {
		return this.parts;
	}

	protected void setParts(Map<String, String> parts) {
		this.parts = parts;
	}

	@Override
	public boolean implies(Permission p) {
		if (!(p instanceof LwxfWildcarkPermission)) {
			return false;
		}

		LwxfWildcarkPermission wp = (LwxfWildcarkPermission) p;

		Map<String,String> otherParts = wp.getParts();

		String otherPerm;
		String perm;
		for (Map.Entry<String,String> otherPart : otherParts.entrySet()) {
			otherPerm = otherPart.getValue();
			perm = this.parts.get(otherPart.getKey());
			if(LwxfStringUtils.isEmpty(perm)){
				return false;
			}
			if(perm.equals(ShiroUtil.WILDCARD_TOKEN)){
				return true;
			}
			String[] otherPermIdxs = otherPerm.contains(",") ? otherPerm.split(",") : new String[]{otherPerm};
			int idx;
			for(int i=0;i<otherPermIdxs.length;i++){
				idx = Integer.parseInt(otherPermIdxs[i]);
				if(idx >=perm.length()){
					throw new LwxfIllegalArgumentException("非法的参数，请确认分隔符:后边的值是否正确");
				}

				if(WebConstant.STRING_ONE.equals(String.valueOf(perm.charAt(idx)))){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return parts.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LwxfWildcarkPermission) {
			LwxfWildcarkPermission wp = (LwxfWildcarkPermission) obj;
			return parts.equals(wp.parts);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		for (Map.Entry<String,String> part : parts.entrySet()) {
			buffer.append(part.getKey())
				.append(ShiroUtil.PERMISSION_PART_DIVIDER_TOKEN)
				.append(part.getValue());
		}
		return buffer.toString();
	}
}
