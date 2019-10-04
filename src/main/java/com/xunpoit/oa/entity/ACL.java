package com.xunpoit.oa.entity;

public class ACL {
	//常量定义
	public static final String  TYPE_ROLE = "Role";
	
	public static final String  TYPE_USER = "User";
	
	public static final int  ACL_YES = 1;
	
	public static final int  ACL_NO = 0;
	//不确定
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
	
	//授权方法
	public void setPermission(int permission, boolean yes) {
		//定义一个临时变量
		int temp = 1;
		//左移permission位
		temp = temp<<permission;
		//yes 为真则加权限  为假为减权限
		if(yes) {
			//按位或运算
			aclState = aclState | temp;
		}else {
			//按位取反
			temp = ~ temp;
			//按位与运算
			aclState = aclState & temp;
		}
	}
	
	//认证方法
	
	public int getPermission(int permission) {
		//如果extendState=1,表示是继承
		//使用角色的权限，所以返回不确定（因为要看角色的权限情况）
		if(extendState==1) {
			return ACL_NEUTRAL;
		}
		//计算某一位上的值是否为1
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