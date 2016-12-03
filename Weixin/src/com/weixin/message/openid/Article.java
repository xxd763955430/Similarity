package com.weixin.message.openid;
//单个图文消息
public class Article {
	private String thumb_media_id;
	private String author;
	private String title;
	private String content_source_url;
	private String content;
	private String digest;
	private int show_cover_pic;
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent_source_url() {
		return content_source_url;
	}
	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getShow_cover_pic() {
		return show_cover_pic;
	}
	public void setShow_cover_pic(int show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
	/*群发图文消息
		WeixinMedia media=MaterialUtil.uploadImage(accessToken, "image.jpg", "F:1.jpg");
		String url=media.getUrl();
		WeixinMedia media1=AdvancedUtil.uploadMedia(accessToken, "thumb", "http://xxd763955430.eicp.net/chapter-06/img/1.jpg");
		String mediaid=media1.getMediaId();
		Map<String,List<Article>> articleMap=new HashMap<String, List<Article>>();
		List<Article> articles=new ArrayList<Article>();
		Article article=new Article();
		article.setThumb_media_id(mediaid);
		article.setTitle("学生会招新");
		article.setContent(String.format("content<img src=%s/>",url).replace("\"","\'"));
		Article article1=new Article();
		article1.setThumb_media_id(mediaid);
		article1.setContent("照片");
		article1.setTitle("title");
		articles.add(article);
		articles.add(article1);
		articleMap.put("articles", articles);
		String newsMediaid=MaterialUtil.uploadArticle(articleMap, accessToken);
		WeixinUserList user=AdvancedUtil.getUserList(accessToken, null);
		List<String> openId= user.getOpenIdList();
		String name= AdvancedUtil.getUserInfo(accessToken,openId.get(0)).getNickname();
		System.out.println(name);
		NewsMessage newsmessage=new NewsMessage();
		newsmessage.setMsgtype("mpnews");
		newsmessage.setTouser(openId);
		Map<String,String> mpnew=new HashMap<String, String>();
		mpnew.put("media_id", newsMediaid);
		newsmessage.setMpnews(mpnew);
		AdvancedUtil.sendMsgByOpenId(accessToken, openId, newsmessage);
	*/
	
}
