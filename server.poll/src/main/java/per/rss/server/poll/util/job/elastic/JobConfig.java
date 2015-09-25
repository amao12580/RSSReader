package per.rss.server.poll.util.job.elastic;

import per.rss.core.base.util.StringUtils;

public class JobConfig<T> {
	/** 任务唯一编码 */
	private String id;
	/** 任务名称 */
	private String name;
	/** 任务运行时间表达式 */
	private String cronExpression;
	/** 任务执行时的参数 */
	private T param;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
