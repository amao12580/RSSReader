package per.rss.server.poll.dao.article;

import java.util.Set;

import per.rss.server.poll.model.feed.piece.Article;

public interface ArticleDao {
	public void insertNews(Set<Article> articles);
}
