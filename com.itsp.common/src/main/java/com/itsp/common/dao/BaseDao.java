package com.itsp.common.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public class BaseDao<T> extends HibernateDaoSupport {
	protected final Class<T> entityClass;

	public BaseDao() {
		this.entityClass = getInitEntityClass();
		if (this.entityClass == null) {
			String clsName = super.getClass().getSimpleName();
			throw new RuntimeException(super.getClass().getCanonicalName()
					+ "未定义泛型! 继承于:" + BaseDao.class.getCanonicalName()
					+ "的类都必需声明所操作实体的泛型, 如:\npublic class " + clsName
					+ " extends " + BaseDao.class.getSimpleName() + "<"
					+ clsName.substring(0, clsName.length() - 3)
					+ "> implements I" + clsName + "{\n\t...\n}");
		}
	}

	public T load(Long id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public Long save(T entity) {
		return (Long) this.getHibernateTemplate().save(entity);
	}

	public void saveBatch(List<T> entitys) {
		for (T entity : entitys) {
			save(entity);
		}
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public void updateBatch(List<T> entitys) {
		for (T entity : entitys) {
			update(entity);
		}
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void deleteBatch(List<T> entitys) {
		for (T entity : entitys) {
			delete(entity);
		}
	}

	public List<T> findByCriteria(DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult,
			int maxResults) {
		return this.getHibernateTemplate().findByCriteria(criteria,
				firstResult, maxResults);
	}

	protected Class<T> getInitEntityClass() {
		Class<T> cls = getEntityTypeFromClass(super.getClass());
		if (cls == null) {
			cls = getFirstGenericType(super.getClass());
		}
		return cls;
	}

	protected Class<T> getEntityTypeFromClass(Class<?> beanClass) {
		if (BaseDao.class.isAssignableFrom(beanClass)) {
			Type type = beanClass.getGenericSuperclass();
			if ((type != null) && (ParameterizedType.class.isInstance(type))) {
				ParameterizedType pType = (ParameterizedType) type;
				if (pType.getRawType().equals(BaseDao.class)) {
					Type argType = pType.getActualTypeArguments()[0];
					if (argType instanceof Class) {
						return ((Class<T>) argType);
					}
					return null;
				}
			}

			return getEntityTypeFromClass(beanClass.getSuperclass());
		}
		return null;
	}

	private Class<T> getFirstGenericType(Class<?> beanClass) {
		Type type = beanClass.getGenericSuperclass();
		if ((type != null) && (ParameterizedType.class.isInstance(type))) {
			ParameterizedType pType = (ParameterizedType) type;
			Type argType = pType.getActualTypeArguments()[0];
			if (argType instanceof Class) {
				return ((Class<T>) argType);
			}
			return null;
		}

		return getFirstGenericType(beanClass.getSuperclass());
	}
}
