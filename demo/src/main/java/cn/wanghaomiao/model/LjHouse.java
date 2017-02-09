package cn.wanghaomiao.model;

import cn.wanghaomiao.seimi.annotation.Xpath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LjHouse {
    private Integer id;

    @Xpath("//h1[@class='main']/text()|//div[@class='sub']/title/text()")
    private String title;

    @Xpath("//span[@class='total']/text()")
    private String totalPrice;//总价
    
    @Xpath("//span[@class='unitPriceValue']/text()")
    private String unitPrice;//单价
    
    @Xpath("//div[@class='room']/div[@class='mainInfo']/text()")
    private String roomMainInfo;//两室一厅
    @Xpath("//div[@class='room']/div[@class='subInfo']/text()")
    private String roomSubInfo;//低楼层/共x层
    
    @Xpath("//div[@class='type']/div[@class='mainInfo']/text()")
    private String typeMainInfo;//南北
    @Xpath("//div[@class='type']/div[@class='subInfo']/text()")
    private String typeSubInfo;//精装
    
    @Xpath("//div[@class='area']/div[@class='mainInfo']/text()")
    private String areaMainInfo;//面积
    @Xpath("//div[@class='area']/div[@class='subInfo']/text()")
    private String areaSubInfo;//xx年建
    
    @Xpath("//div[@class='communityName']/a[@class='info']/text()")
    private String community;//小区
    
    @Xpath("//div[@class='areaName']/span[@class='info']/a[first()]/text()")
    private String position1;//所处区
    @Xpath("//div[@class='areaName']/span[@class='info']/a[last()]/text()")
    private String position2;//所处片
    @Xpath("//div[@class='areaName']/span[@class='info']/text()")
    private String position3;//位置几环
    @Xpath("//div[@class='areaName']/a[@class='supplement']/text()")
    private String position4;//附加信息

    //也可以这么写 @Xpath("//div[@id='cnblogs_post_body']//text()")
    @Xpath("//div[@class='content']/allText()")
    private String content;
    
    private String url;
    
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
