package com.xunpoit.oa.entity;

import java.util.List;

/**
 * ��֯����ʵ���� ���νṹ���и��������ӻ���
 * 
 * @author Administrator
 *
 */
public class Org {
	// ���
	private Integer id;

	private String name;
	// �������� �����ɹ��� ��������+"_"+�Լ���ţ�
	// ���û�������������Լ���ţ�
	private String sn;
	// ����
	private String description;
	// ������
	private Org parent;
	// �ӻ���
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