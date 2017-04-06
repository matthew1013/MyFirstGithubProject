package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 任务所需技能实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-04-03
 * 
 */
public class DemandSpecialty implements Serializable {
	private static final long serialVersionUID = -9064749783864443308L;
	private String id; // 编号，自动生成
	private String demandId; //任务id
	private String specialtyTypeId; //专长类型id
	private String specialtyId; //专长id
	
	private Specialty specialty;
	private SpecialtyType st;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDemandId() {
		return demandId;
	}
	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}
	public String getSpecialtyTypeId() {
		return specialtyTypeId;
	}
	public void setSpecialtyTypeId(String specialtyTypeId) {
		this.specialtyTypeId = specialtyTypeId;
	}
	public String getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	public Specialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
	public SpecialtyType getSt() {
		return st;
	}
	public void setSt(SpecialtyType st) {
		this.st = st;
	}
	
	
}
