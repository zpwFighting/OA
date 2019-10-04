package com.xunpoit.oa.entity;

public class ACL {
	//��������
	public static final String  TYPE_ROLE = "Role";
	
	public static final String  TYPE_USER = "User";
	
	public static final int  ACL_YES = 1;
	
	public static final int  ACL_NO = 0;
	//��ȷ��
	public static final int   ACL_NEUTRAL = -1;
	
    private Integer id;

    private String mainType;

    private int mainId;

    private int moduleId;

    private int aclState;

    private int extendState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType == null ? null : mainType.trim();
    }

	public int getMainId() {
		return mainId;
	}

	public void setMainId(int mainId) {
		this.mainId = mainId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getAclState() {
		return aclState;
	}

	public void setAclState(int aclState) {
		this.aclState = aclState;
	}

	public int getExtendState() {
		return extendState;
	}

	public void setExtendState(int extendState) {
		this.extendState = extendState;
	}
	
	//��Ȩ����
	public void setPermission(int permission, boolean yes) {
		//����һ����ʱ����
		int temp = 1;
		//����permissionλ
		temp = temp<<permission;
		//yes Ϊ�����Ȩ��  Ϊ��Ϊ��Ȩ��
		if(yes) {
			//��λ������
			aclState = aclState | temp;
		}else {
			//��λȡ��
			temp = ~ temp;
			//��λ������
			aclState = aclState & temp;
		}
	}
	
	//��֤����
	
	public int getPermission(int permission) {
		//���extendState=1,��ʾ�Ǽ̳�
		//ʹ�ý�ɫ��Ȩ�ޣ����Է��ز�ȷ������ΪҪ����ɫ��Ȩ�������
		if(extendState==1) {
			return ACL_NEUTRAL;
		}
		//����ĳһλ�ϵ�ֵ�Ƿ�Ϊ1
		int temp = 1;
		temp<<=permission;
		temp&=aclState;
		if(temp>0) {
			return ACL_YES;
		}else {
			return ACL_NO;
		}
		
	}
	

}