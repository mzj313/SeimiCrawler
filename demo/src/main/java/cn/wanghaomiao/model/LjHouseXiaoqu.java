package cn.wanghaomiao.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import cn.wanghaomiao.seimi.annotation.Xpath;

public class LjHouseXiaoqu {
    private Integer id;

    @Xpath("//div[@class='title']/a/text()")
    private String title;

    @Xpath("//div[@class='houseInfo']/a[first()]/text()")
    private String houseInfo;//30天成交几套
    
    @Xpath("//div[@class='positionInfo']/a[first()]/text()")
    private String positionInfo1;//区
    @Xpath("//div[@class='positionInfo']/a[last()]/text()")
    private String positionInfo2;//片
    @Xpath("//div[@class='positionInfo']/text()")
    private String positionInfo;//
    @Xpath("//div[@class='tagList']/span/text()")
    private String tagList;//
    
    @Xpath("//div[@class='xiaoquListItemPrice']/div[@class='totalPrice']/span/text()")
    private String averagePrice;//小区参考均价
    @Xpath("//div[@class='xiaoquListItemSellCount']/a/span/text()")
    private String num;//在售套数
    
    @Xpath("//div[@class='title']/a/@href")
    private String url;
    
    private Integer totalPage;
    
    public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getHouseInfo() {
		return houseInfo;
	}

	public void setHouseInfo(String houseInfo) {
		this.houseInfo = houseInfo;
	}

	public String getPositionInfo1() {
		return positionInfo1;
	}

	public void setPositionInfo1(String positionInfo1) {
		this.positionInfo1 = positionInfo1;
	}

	public String getPositionInfo2() {
		return positionInfo2;
	}

	public void setPositionInfo2(String positionInfo2) {
		this.positionInfo2 = positionInfo2;
	}

	public String getPositionInfo() {
		return positionInfo;
	}

	public void setPositionInfo(String positionInfo) {
		this.positionInfo = positionInfo;
	}

	public String getTagList() {
		return tagList;
	}

	public void setTagList(String tagList) {
		this.tagList = tagList;
	}

	public String getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(String totalPrice) {
		this.averagePrice = totalPrice;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
