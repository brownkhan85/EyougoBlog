package com.eyougo.blog.dao;

import java.util.List;

import com.eyougo.blog.comm.OrderProperty;
import com.eyougo.blog.entity.Blog;
import com.eyougo.blog.entity.Category;

/**
 * ��־�־ò�
 * @author MeiHongyuan
 *
 */
public interface BlogDao {
	/**
	 * ������־
	 * @param blog
	 * 					Blog
	 * @return Blog
	 */
	Blog saveBlog(Blog blog);
	/**
	 * ������־Id��ѯ����־��Ϣ
	 * @param blogId
	 * 					Integer
	 * @return Blog
	 */
	Blog findBlogById(Integer blogId);
	/**
	 * ��������ͷ�ҳ������ѯ��־��Ϣ
	 * @param op
	 * @param firstResult
	 * @param maxResults
	 */
	List<Blog> findAllBlogList(OrderProperty[] op,
			int firstResult, int maxResults);
	
	/**
	 * ���������������ͷ�ҳ������ѯ��־��Ϣ
	 * @param category
	 * @param op
	 * @param firstResult
	 * @param maxResults
	 * @return List<Blog>
	 */
	List<Blog> findBlogListByCategory(Category category, OrderProperty[] op, 
			int firstResult, int maxResults);
	/**
	 * ȡ��������־������
	 * @return long
	 */
	long getAllBlogNum();
	
	/**
	 * 
	 * @param category
	 * @return
	 */
	long getBlogNumByCategory(Category category);
	
	/**
	 * 
	 * @param blog
	 * @return
	 */
	long findBlogNumLikeBlog(Blog blog);
	
	/**
	 * 
	 * @param blog
	 * @param op
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Blog> findBlogListLikeBlog(Blog blog, OrderProperty[] op, 
			int firstResult, int maxResults); 
	/**
	 * 
	 * @param blog
	 */
	void deleteBlog(Blog blog);
	
	/**
	 * ������������blog���Ƶ�Blog��Ȼ��propertyNames���з���ͳ������
	 * @param blog
	 * @param propertyNames
	 * @return
	 */
	////2.0Ӧ���ع�������������ɷ���Dao��ʽ����������������
	public List findLikeBlogNumGroupByProperties(Blog blog,String[] propertyNames );
	
	/**
	 * ����ָ��������˳���ø���Blog���ʱ��֮ǰ�ĵ�һ��Blog���󣬲��������
	 * @param blog
	 * @param op
	 * @return
	 */
	public Blog findBlogBeforeThisBlog(Blog blog,OrderProperty[] op);
	
	/**
	 * ����ָ��������˳���ø���Blog���ʱ��֮��ĵ�һ��Blog���󣬲��������
	 * @param blog
	 * @param op
	 * @return
	 */
	public Blog findBlogAfterThisBlog(Blog blog,OrderProperty[] op);
	
}
