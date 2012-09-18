package com.eyougo.blog.biz.impl;

import java.util.Iterator;
import java.util.List;

import com.eyougo.blog.base.exception.InternalException;
import com.eyougo.blog.biz.BlogBiz;
import com.eyougo.blog.comm.EyougoConstant;
import com.eyougo.blog.comm.OrderProperty;
import com.eyougo.blog.comm.Pager;
import com.eyougo.blog.dao.BlogDao;
import com.eyougo.blog.dao.CategoryDao;
import com.eyougo.blog.entity.Blog;
import com.eyougo.blog.entity.Category;

public class BlogBizImpl implements BlogBiz {
	private BlogDao blogDao;
	private CategoryDao categoryDao;

	@Override
	public Blog saveBlog(Blog blog) throws InternalException {
		try {
			blog = this.getBlogDao().saveBlog(blog);
			return blog;
		} catch (Exception e) {
			throw new InternalException(e);
		}
	}

	@Override
	public Blog getBlogById(Integer blogId) {
		Blog blog = this.getBlogDao().findBlogById(blogId);
		return blog;
	}

	@Override
	public List<Blog> getRecentPublishedBlogs(int recentNum) {
		OrderProperty[] op = { new OrderProperty("date", EyougoConstant.ORDER_DESC) };
		Blog blog = new Blog();
		blog.setIsDraft(false);
		List<Blog> blogs = this.getBlogDao().findBlogListLikeBlog(blog, op, 0, recentNum);
		return blogs;
	}

	@Override
	public List<Blog> getBlogsByCategory(Category category, Pager pager) {
		OrderProperty[] op = { new OrderProperty("top", EyougoConstant.ORDER_DESC), new OrderProperty("date", EyougoConstant.ORDER_DESC) };
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getTotalNum() == -1) {
			pager.setTotalNum(this.getBlogDao().getBlogNumByCategory(category));
		}
		List<Blog> blogs = this.getBlogDao().findBlogListByCategory(category, op, pager.getOffset(), pager.getPerPageNum());
		return blogs;
	}

	@Override
	public List<Blog> getBlogsLikeBlog(Blog blog, Pager pager) {
		OrderProperty[] op = { new OrderProperty("top", EyougoConstant.ORDER_DESC), new OrderProperty("date", EyougoConstant.ORDER_DESC) };
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getTotalNum() == -1) {
			pager.setTotalNum(this.getBlogDao().findBlogNumLikeBlog(blog));
		}
		List<Blog> blogs = this.getBlogDao().findBlogListLikeBlog(blog, op, pager.getOffset(), pager.getPerPageNum());
		return blogs;
	}

	@Override
	public long getAllBlogNum() {
		long l = this.getBlogDao().getAllBlogNum();
		return l;
	}

	@Override
	public boolean deleteBlogById(Integer blogId) throws InternalException {
		boolean deleteSuccess = false;
		try {
			Blog blog = this.getBlogDao().findBlogById(blogId);
			if (blog != null) {
				this.getBlogDao().deleteBlog(blog);
				deleteSuccess = true;
			}
			return deleteSuccess;
		} catch (Exception e) {
			throw new InternalException(e);
		}
	}

	@Override
	public int updateBlogsToPublish(List<String> blogIds) throws InternalException {
		// 考虑对日志的批量操作并不频繁和大量，故未采用批量更新方式
		int sucNum = 0;
		try {
			for (Iterator<String> iterator = blogIds.iterator(); iterator.hasNext();) {
				String blogIdStr = iterator.next();
				Integer blogId = null;
				if (!blogIdStr.equals("false")) {
					blogId = Integer.parseInt(blogIdStr);
				}
				Blog blog = this.getBlogById(blogId);
				blog.setIsDraft(false);
				this.saveBlog(blog);
				sucNum++;
			}
			return sucNum;
		} catch (Exception e) {
			throw new InternalException(e);
		}
	}

	@Override
	public int updateBlogsToCategory(List<String> blogIds, Integer categoryId) throws InternalException {
		int sucNum = 0;
		try {
			for (Iterator<String> iterator = blogIds.iterator(); iterator.hasNext();) {
				String blogIdStr = iterator.next();
				Integer blogId = null;
				if (!blogIdStr.equals("false")) {
					blogId = Integer.parseInt(blogIdStr);
				}
				Blog blog = this.getBlogById(blogId);
				Category category = this.getCategoryDao().loadCategoryById(categoryId);
				blog.setCategory(category);
				this.saveBlog(blog);
				sucNum++;
			}
			return sucNum;
		} catch (Exception e) {
			throw new InternalException(e);
		}
	}

	@Override
	public int deleteBlogs(List<String> blogIds) throws InternalException {
		int sucNum = 0;
		try {
			for (Iterator<String> iterator = blogIds.iterator(); iterator.hasNext();) {
				String blogIdStr = iterator.next();
				Integer blogId = null;
				if (!blogIdStr.equals("false")) {
					blogId = Integer.parseInt(blogIdStr);
				}
				boolean suc = this.deleteBlogById(blogId);
				if (suc) {
					sucNum++;
				}
			}
			return sucNum;
		} catch (Exception e) {
			throw new InternalException(e);
		}
	}

	@Override
	public List<Blog> getBlogNumGroupByCategory() {
		Blog blog = new Blog();
		blog.setIsDraft(false);
		List<Blog> list = this.getBlogDao().findLikeBlogNumGroupByProperties(blog, new String[] { "category" });
		return list;
	}

	@Override
	public Blog getBlogBeforeThisBlog(Blog blog) {
		OrderProperty[] op = { new OrderProperty("date", EyougoConstant.ORDER_DESC) };
		Blog bBlog = this.getBlogDao().findBlogBeforeThisBlog(blog, op);
		return bBlog;
	}

	@Override
	public Blog getBlogAfterThisBlog(Blog blog) {
		OrderProperty[] op = { new OrderProperty("date", EyougoConstant.ORDER_ASC) };
		Blog aBlog = this.getBlogDao().findBlogAfterThisBlog(blog, op);
		return aBlog;
	}

	public BlogDao getBlogDao() {
		return blogDao;
	}

	public void setBlogDao(BlogDao blogDao) {
		this.blogDao = blogDao;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

}