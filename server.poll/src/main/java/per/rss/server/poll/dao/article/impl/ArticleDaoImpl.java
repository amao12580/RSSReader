package per.rss.server.poll.dao.article.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import per.rss.core.nosql.mongo.MongoBaseDao;
import per.rss.server.poll.dao.article.ArticleDao;
import per.rss.server.poll.model.feed.piece.Article;

@Repository(value = "articleDao")
public class ArticleDaoImpl implements ArticleDao {

	@Autowired(required = true)
	private MongoBaseDao<Article> mongoBaseDao;

	@Override
	public void insertNews(Set<Article> articles) {
		this.mongoBaseDao.insertAll(articles);
	}
}
