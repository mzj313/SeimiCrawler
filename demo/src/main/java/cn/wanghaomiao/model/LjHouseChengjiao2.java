package cn.wanghaomiao.model;

import cn.wanghaomiao.seimi.annotation.Xpath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

//从列表页获取
public class LjHouseChengjiao2 {
    private Integer id;

    @Xpath("//div[@class='title']/a/text()")
    private String title;

    @Xpath("//div[@class='totalPrice']/span/text()")
    private String totalPrice;//总价
    @Xpath("//div[@class='unitPrice']/span/text()")
    private String unitPrice;//单价
    @Xpath("//div[@class='title']/a/text()")
    private String roomMainInfo;//x室x厅
    @Xpath("//div[@class='positionInfo']/text()")
    private String roomSubInfo;//低楼层/共x层
    
    @Xpath("//div[@class='houseInfo']/text()")
    private String typeMainInfo;//南北
    @Xpath("//div[@class='houseInfo']/text()")
    private String typeSubInfo;//精装
    
    @Xpath("//div[@class='title']/a/text()")
    private String areaMainInfo;//xx平米
    @Xpath("//div[@class='positionInfo']/text()")
    private String areaSubInfo;//xx年建
    
    @Xpath("//div[@class='title']/a/text()")
    private String community;//小区
    
    private String position1;//所处区
    private String position2;//所处片
    
    private String position3;//位置几环
    @Xpath("//div[@class='dealDate']/text()")
    private String position4;//附加信息
    @Xpath("//div[@class='dealDate']/text()")
    private String dealDate;//附加信息

    @Xpath("//span[@class='dealHouseTxt']/span/text()")
    private String content;
    
    private String url;
    
    private String rid;
    
    public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPosition4() {
		return position4;
	}

	public void setPosition4(String position4) {
		this.position4 = position4;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getPosition1() {
		return position1;
	}

	public void setPosition1(String position1) {
		this.position1 = position1;
	}

	public String getPosition2() {
		return position2;
	}

	public void setPosition2(String position2) {
		this.position2 = position2;
	}

	public String getPosition3() {
		return position3;
	}

	public void setPosition3(String position3) {
		this.position3 = position3;
	}

	public String getRoomMainInfo() {
		return roomMainInfo;
	}

	public void setRoomMainInfo(String roomMainInfo) {
		this.roomMainInfo = roomMainInfo;
	}

	public String getRoomSubInfo() {
		return roomSubInfo;
	}

	public void setRoomSubInfo(String roomSubInfo) {
		this.roomSubInfo = roomSubInfo;
	}

	public String getTypeMainInfo() {
		return typeMainInfo;
	}

	public void setTypeMainInfo(String typeMainInfo) {
		this.typeMainInfo = typeMainInfo;
	}

	public String getTypeSubInfo() {
		return typeSubInfo;
	}

	public void setTypeSubInfo(String typeSubInfo) {
		this.typeSubInfo = typeSubInfo;
	}

	public String getAreaMainInfo() {
		return areaMainInfo;
	}

	public void setAreaMainInfo(String areaMainInfo) {
		this.areaMainInfo = areaMainInfo;
	}

	public String getAreaSubInfo() {
		return areaSubInfo;
	}

	public void setAreaSubInfo(String areaSubInfo) {
		this.areaSubInfo = areaSubInfo;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        if (StringUtils.isNotBlank(content)&&content.length()>100){
            //方便查看截断下
            this.content = StringUtils.substring(content,0,100)+"...";
        }
        return ToStringBuilder.reflectionToString(this);
    }
}
