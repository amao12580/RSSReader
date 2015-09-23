package per.rss.server.poll.model.job;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import per.rss.core.base.util.StringUtils;

/**
 * 计划任务信息
 * 
 * User: liyd Date: 14-1-3 Time: 上午10:24
 */
@Document(collection = "scheduleJob") 
public class ScheduleJob implements Serializable{

	private static final long serialVersionUID = 4103005449292220543L;

	/** 任务id */
	private Long scheduleJobId;

	/** 任务名称 */
	private String jobName;

	/** 任务别名 */
	private String aliasName;

	/** 任务分组 */
	private String jobGroup;

	/** 触发器 */
	private String jobTrigger;

	/** 任务状态 */
	private String status;

	/** 任务运行时间表达式 */
	private String cronExpression;

	/** 是否异步 */
	private Boolean isSync;

	/** 任务描述 */
	private String description;

	/** 创建时间 */
	private Date gmtCreate;

	/** 修改时间 */
	private Date gmtModify;
	
	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}

	public Long getScheduleJobId() {
		return scheduleJobId;
	}

	public void setScheduleJobId(Long scheduleJobId) {
		this.scheduleJobId = scheduleJobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobTrigger() {
		return jobTrigger;
	}

	public void setJobTrigger(String jobTrigger) {
		this.jobTrigger = jobTrigger;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Boolean getIsSync() {
		return isSync;
	}

	public void setIsSync(Boolean isSync) {
		this.isSync = isSync;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
}
