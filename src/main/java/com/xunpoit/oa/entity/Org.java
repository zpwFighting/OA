package com.xunpoit.oa.entity;

import java.util.List;

/**
 * 组织机构实体类 树形结构，有父机构和子机构
 * 
 * @author Administrator
 *
 */
public class Org {
	// 编号
	private Integer id;

	private String name;
	// 机构编码 ：生成规则 顶级机构+"_"+自己编号；
	// 如果没顶级机构就是自己编号；
	private String sn;
	// 描述
	private String description;
	// 父机构
	private Org parent;
	// 子机构
	private List<Org> childList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn == null ? null : sn.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}

	public List<Org> getChildList() {
		return childList;
	}

	public void setChildList(List<Org> childList) {
		this.childList = childList;
	}

	@Override
	public String toString() {
		return "Org [id=" + id + ", name=" + name + ", sn=" + sn + ", description=" + description + ", parent=" + parent
				+ ", childList=" + childList + "]";
	}
	

}